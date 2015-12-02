package nl.naturalis.oaipmh;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.rest.OAIPMHResource;

import org.domainobject.util.ConfigObject;

/**
 * Factory class producing {@link IOAIRepository} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class RepositoryFactory {

	private static RepositoryFactory instance;

	/**
	 * Returns a factory for {@link IOAIRepository} instances.
	 * 
	 * @return
	 */
	public static RepositoryFactory getInstance()
	{
		if (instance == null)
			instance = new RepositoryFactory();
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
	 * actual {@link IOAIRepository} instance as follows. First the
	 * configuration file for the REST service is checked to see if it contains
	 * a property named &#34;repository.${repoName}.config&#34;. If so, its
	 * value is taken to be the full path to the configuration file for the OAI
	 * repository. If the configuration for the REST service does <i>not</i>
	 * contain this property, the classpath is searched for the repository
	 * configuration file. The name of the repository configuration file then is
	 * assumed to be &#034;oai-repo.${repoName}.properties&#034;. The repository
	 * configuration file must be a standard Java properties file and it
	 * <b>must</b> contain at least one property: &#34;repo.impl.class&#34;,
	 * which must specify the fully-qualified name of the class implementing
	 * {@link IOAIRepository}.
	 * 
	 * @param repoName
	 * @return
	 * @throws RepositoryInitializationException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IOAIRepository create(String repoName) throws RepositoryInitializationException
	{
		IOAIRepository repo = cache.get(repoName);
		if (repo == null) {
			ConfigObject restConfig = Registry.getInstance().getConfig();
			String property = "repository." + repoName + ".config";
			String repoConfigFile = restConfig.get(property);
			ConfigObject repoConfig = null;
			if (repoConfigFile == null) {
				repoConfigFile = "oai-repo." + repoName + ".properties";
				InputStream is = getClass().getResourceAsStream(repoConfigFile);
				if (is != null)
					repoConfig = new ConfigObject(is);
			}
			else {
				File f = new File(repoConfigFile);
				if (f.isFile())
					repoConfig = new ConfigObject(f);
			}
			if (repoConfig == null) {
				String fmt = "Missing configuration file for OAI repository \"%s\"";
				String msg = String.format(fmt, repoName);
				throw new RepositoryInitializationException(msg);
			}
			String repoClassName = repoConfig.required("repo.impl.class");
			Class<IOAIRepository> repoClass;
			try {
				repoClass = (Class<IOAIRepository>) Class.forName(repoClassName);
			}
			catch (ClassNotFoundException e) {
				String fmt = "Missing implementation for OAI repository \"%s\" (%s)";
				String msg = String.format(fmt, repoName, repoClassName);
				throw new RepositoryInitializationException(msg);
			}
			try {
				repo = repoClass.newInstance();
				cache.put(repoName, repo);
			}
			catch (InstantiationException | IllegalAccessException e) {
				throw new RepositoryInitializationException(e);
			}
		}
		return repo;
	}

}
