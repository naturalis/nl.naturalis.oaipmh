package nl.naturalis.oaipmh.rest;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.util.ConfigObject;
import nl.naturalis.oaipmh.util.FileUtil;
import nl.naturalis.oaipmh.util.IOUtil;

/**
 * Factory class producing {@link IOAIRepository} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class RepositoryFactory {

  private static final String REPO_CONFIG_FILENAME_PATTERN = "oai-repo.%s.properties";
  private static final Logger logger = LoggerFactory.getLogger(RepositoryFactory.class);

  private static RepositoryFactory instance;

  /**
   * Returns a factory for {@link IOAIRepository} instances.
   * 
   * @return
   */
  public static RepositoryFactory getInstance() {
    if (instance == null) {
      instance = new RepositoryFactory();
    }
    return instance;
  }

  private RepositoryFactory() {}

  private HashMap<String, IOAIRepository> cache = new HashMap<>();

  /**
   * Returns an {@link IOAIRepository} instance of the OAI repository with the specified name. This name is resolved to an actual
   * {@link IOAIRepository} instance as follows:
   * <ul>
   * <li>First the configuration file for the REST service itself (oaipmh.properties) is checked to see if it contains a property
   * named <code>repository.&#36;{repoGroup}.config</code>. Thus, given a base URL of http://example.com/oaipmh, a request for
   * http://example.com/oaipmh/my-repo first results in oaipmh.properties being searched for a property named
   * repository.my-repo.config. If this property is present, its value is taken to be the full path to the configuration file for
   * the OAI repository.
   * <li>If oaipmh.properties does not contain this property, the classpath is searched for the repository configuration file. The
   * name of the repository configuration file is assumed to be <code>oai-repo.&#36;{repoGroup}.properties</code>.
   * <li>The repository configuration file must be a standard Java properties file and it must contain at least one property. When
   * using repository groups, the property must be named <code>&#36;{repoName}.repo.impl.class</code>. Otherwise it must be named
   * <code>repo.impl.class</code>. In either case it must specify the fully-qualified name of the class implementing
   * {@link IOAIRepository}.
   * <li>Finally, the class specified by <code>repo.impl.class</code> or <code>&#36;{repoName}.repo.impl.class</code> is
   * instantiated using its no-arg constructor.
   * </ul>
   * See {@link OAIPMHResource} for an explanation of repository groups.
   * 
   * @param repoGroup
   * @param repoName
   * @return
   * @throws RepositoryInitializationException
   * 
   * @see {@link OAIPMHResource}
   * 
   */
  @SuppressWarnings("unchecked")
  public IOAIRepository build(String repoGroup, String repoName) throws RepositoryInitializationException {
    String cacheKey = "%" + repoGroup + "/" + repoName + "%";
    logger.debug("Searching cache for requested repository (group={};name={})", repoGroup, repoName);
    IOAIRepository repository = cache.get(cacheKey);
    if (repository == null) {
      ConfigObject config = getRepoConfigFromRestConfig(repoGroup);
      if (config == null) {
        config = getRepoConfigFromClasspath(repoGroup);
      }
      if (config == null) {
        String msg = String.format("Missing configuration file for OAI repository \"%s\"", repoGroup);
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
      } catch (ClassNotFoundException e) {
        String fmt = "Repository implementation class (%s) not found";
        String msg = String.format(fmt, repoClassName);
        throw new RepositoryInitializationException(msg);
      }
      try {
        repository = repoClass.getConstructor(new Class<?>[0]).newInstance();
        repository.setConfiguration(config);
        cache.put(cacheKey, repository);
      } catch (Exception e) {
        throw new RepositoryInitializationException(e);
      }
    }
    return repository;
  }

  private static ConfigObject getRepoConfigFromRestConfig(String repoGroup)
      throws RepositoryInitializationException {
    ConfigObject restConfig = Registry.getInstance().getConfig();
    String pattern = REPO_CONFIG_FILENAME_PATTERN;
    String key = String.format(pattern, repoGroup) + ".file";
    String val = restConfig.get(key);
    if (Strings.isBlank(val)) {
      return null;
    }
    logger.debug("Searching for {}", val);
    File f = new File(val);
    if (!f.isFile()) {
      String msg = String.format("Invalid value for property \"%s\" in %s. No such file: \"%s\"", key, val);
      throw new RepositoryInitializationException(msg);
    }
    logConfigLocation(repoGroup, key);
    return new ConfigObject(f);
  }

  private ConfigObject getRepoConfigFromClasspath(String repoGroup) {
    File confDir = Registry.getInstance().getConfDir();
    String cfgFileName = String.format(REPO_CONFIG_FILENAME_PATTERN, repoGroup);
    logger.debug("Searching for {} in {}", cfgFileName, confDir.getAbsolutePath());
    File file = FileUtil.newFile(confDir, cfgFileName);
    if (file.isFile()) {
      logConfigLocation(repoGroup, file.getAbsolutePath());
      return new ConfigObject(file);
    }
    logger.debug("Searching classpath for file {}", file.getAbsolutePath());
    InputStream is = getClass().getResourceAsStream(cfgFileName);
    if (is == null) {
      cfgFileName = "/" + cfgFileName;
      is = getClass().getResourceAsStream(cfgFileName);
    }
    if (is != null) {
      logConfigLocation(repoGroup, getClass().getResource(cfgFileName).toExternalForm());
      ConfigObject cfg = new ConfigObject(is);
      IOUtil.close(is);
      return cfg;
    }
    return null;
  }

  private static void logConfigLocation(String repoGroup, String location) {
    if (logger.isDebugEnabled()) {
      logger.debug("Loading configuration for {} repositories: {}", repoGroup, location);
    }
  }

}
