package nl.naturalis.oaipmh.rest;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import nl.naturalis.oaipmh.api.IOAIRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.ConfigObject;
import org.domainobject.util.FileUtil;

/**
 * Factory class producing {@link IOAIRepository} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class RepositoryFactory {

	private static final String REPO_CONFIG_FILENAME_PATTERN = "oai-repo.%s.properties";
	private static final Logger logger = LogManager.getLogger(RepositoryFactory.class);

	private static RepositoryFactory instance;

	/**
	 * Returns a factory for {@link IOAIRepository} instances.
	 * 
	 * @return
	 */
	public static RepositoryFactory getInstance()
	{
		if (instance == null) {
			instance = new RepositoryFactory();
		}
		/*
		 * Singletons don't behave nicely within Wildfly container, so we just
		 * create a new instance every time this method is called.
		 */
		return instance;
	}

	private RepositoryFactory()
	{
	}

	private HashMap<String, IOAIRepository> cache = new HashMap<>();

	/**
	 * Returns an {@link IOAIRepository} instance of the OAI repository with the
	 * specified name. The name comes from the path following the base URL of
	 * the REST service (see {@link OAIPMHResource}). The name is resolved to an
	 * actual {@link IOAIRepository} instance as follows:
	 * <ul>
	 * <li>
	 * First the configuration file for the REST service is checked to see if it
	 * contains a property named <b>repository.${repoDescriptor}.config</b>.
	 * Thus, given a base URL of http://example.com/oaipmh, a request for
	 * http://example.com/oaipmh/repo1 first results in oaipmh.properties being
	 * searched for a property named repository.repo1.config. If this property
	 * is present, its value is taken to be the <i>full path</i> to the
	 * configuration file for the OAI repository.
	 * <li>If oaipmh.properties does <i>not</i> contain this property, the
	 * classpath is searched for the repository configuration file. The name of
	 * the repository configuration file then is assumed to be
	 * <b>oai-repo.${repoDescriptor}.properties</b>.
	 * <li>The repository configuration file must be a standard Java properties
	 * file and it must contain at least one property: if the {@code repoName}
	 * argument is {@code null}, the property must be named
	 * <b>repo.impl.class</b>; if the {@code repoName} argument is not
	 * {@code null}, it must be named <b>${repoName}.repo.impl.class</b>. In
	 * either case it must specify the fully-qualified name of the class
	 * implementing {@link IOAIRepository}. Note that this implies that multiple
	 * repositories can share the same configuration file, allowing similar
	 * repositories to be configured in one place. One configuration file can
	 * specify multiple {@link IOAIRepository} implementations.
	 * <li>Finally, the class specified by repo.impl.class property or
	 * ${repoName}.repo.impl.class property is instantiated using its
	 * assumed-to-be-present no-arg constructor.
	 * </ul>
	 * 
	 * @param repoGroup
	 * @param repoName
	 * @return
	 * @throws RepositoryInitializationException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IOAIRepository build(String repoGroup, String repoName)
			throws RepositoryInitializationException
	{
		String cacheKey = "%" + repoGroup + "/" + repoName + "%";
		IOAIRepository repository = cache.get(cacheKey);
		if (repository == null) {
			ConfigObject config = getRepoConfigFromRestConfig(repoGroup);
			if (config == null) {
				config = getRepoConfigFromClasspath(repoGroup);
			}
			if (config == null) {
				String fmt = "Missing configuration file for OAI repository \"%s\"";
				String msg = String.format(fmt, repoGroup);
				throw new RepositoryInitializationException(msg);
			}
			String repoClassName;
			if (repoName == null)
				repoClassName = config.required("repo.impl.class");
			else
				repoClassName = config.required(repoName + ".repo.impl.class");
			Class<IOAIRepository> repoClass;
			try {
				repoClass = (Class<IOAIRepository>) Class.forName(repoClassName);
			}
			catch (ClassNotFoundException e) {
				String fmt = "Repository implementation class (%s) not found";
				String msg = String.format(fmt, repoClassName);
				throw new RepositoryInitializationException(msg);
			}
			try {
				repository = repoClass.newInstance();
				repository.setConfiguration(config);
				cache.put(cacheKey, repository);
			}
			catch (InstantiationException | IllegalAccessException e) {
				throw new RepositoryInitializationException(e);
			}
		}
		return repository;
	}

	private static ConfigObject getRepoConfigFromRestConfig(String repoGroup)
			throws RepositoryInitializationException
	{
		ConfigObject restConfig = Registry.getInstance().getConfig();
		String pattern = REPO_CONFIG_FILENAME_PATTERN;
		String key = String.format(pattern, repoGroup) + ".file";
		String val = restConfig.get(key);
		if (val == null) {
			return null;
		}
		logger.debug("Searching for {}", val);
		File f = new File(val);
		if (!f.isFile()) {
			String fmt = "Invalid value for property \"%s\" in %s. No such file: \"%s\"";
			String msg = String.format(fmt, key, val);
			throw new RepositoryInitializationException(msg);
		}
		logConfigLocation(repoGroup, key);
		return new ConfigObject(f);
	}

	private ConfigObject getRepoConfigFromClasspath(String repoGroup)
	{
		File confDir = Registry.getInstance().getConfDir();
		String cfgFileName = String.format(REPO_CONFIG_FILENAME_PATTERN, repoGroup);
		if (logger.isDebugEnabled()) {
			String fmt = "Searching for {} in {}";
			logger.debug(fmt, cfgFileName, confDir.getAbsolutePath());
		}
		File file = FileUtil.newFile(confDir, cfgFileName);
		if (file.isFile()) {
			logConfigLocation(repoGroup, file.getAbsolutePath());
			return new ConfigObject(file);
		}
		logger.debug("Searching classpath for file {}");
		InputStream is = getClass().getResourceAsStream(cfgFileName);
		if (is == null) {
			cfgFileName = "/" + cfgFileName;
			is = getClass().getResourceAsStream(cfgFileName);
		}
		if (is != null) {
			logConfigLocation(repoGroup, getClass().getResource(cfgFileName).toExternalForm());
			return new ConfigObject(is);
		}
		return null;
	}

	private static void logConfigLocation(String repoGroup, String location)
	{
		logger.info("Configuration file for {} OAI repository: {}", repoGroup, location);
	}

}
