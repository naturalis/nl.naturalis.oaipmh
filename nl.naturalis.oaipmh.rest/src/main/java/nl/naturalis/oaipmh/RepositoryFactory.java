package nl.naturalis.oaipmh;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import nl.naturalis.oaipmh.api.IRepository;

import org.domainobject.util.ConfigObject;

/**
 * A factory class producing {@link IRepository} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class RepositoryFactory {

	private static RepositoryFactory instance;

	public static RepositoryFactory getInstance()
	{
		if (instance == null)
			instance = new RepositoryFactory();
		return instance;
	}

	private RepositoryFactory()
	{
	}

	private HashMap<String, IRepository> cache = new HashMap<>();

	/**
	 * Returns an {@link IRepository} instance for the specified repository
	 * name. The name comes from the first path segment after the base URL of
	 * the REST service. The name is resolved to an {@code IRepository} instance
	 * as follows. First the configuration file for the REST service is checked
	 * to see if it contains a property named "repository.${name}.config". If
	 * so, its value is taken to be the full path to the configuration file for
	 * the OAI repository. If the configuration for the REST service does
	 * <i>not</i> contain this property, the classpath is searched for the
	 * repository configuration file. The file then being searched for is
	 * "oai-repo.${name}.properties". The repository configuration file must be
	 * a standard Java properties file and it must contain at least one
	 * property: "repo.class.name" which must specify the class implementing
	 * {@code IRepository}.
	 * 
	 * @param name
	 * @return
	 * @throws RepositoryInitializationException
	 */
	@SuppressWarnings("unchecked")
	public IRepository create(String name) throws RepositoryInitializationException
	{
		IRepository repo = cache.get(name);
		if (repo == null) {
			ConfigObject restConfig = Registry.getInstance().getConfig();
			String property = "repository." + name + ".config";
			String repoConfigFile = restConfig.get(property);
			ConfigObject repoConfig = null;
			if (repoConfigFile == null) {
				repoConfigFile = "oai-repo." + name + ".properties";
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
				String msg = String.format(fmt, name);
				throw new RepositoryInitializationException(msg);
			}
			String repoClassName = repoConfig.required("repo.class.name");
			Class<IRepository> repoClass;
			try {
				repoClass = (Class<IRepository>) Class.forName(repoClassName);
			}
			catch (ClassNotFoundException e) {
				String fmt = "Missing implementation for OAI repository \"%s\" (%s)";
				String msg = String.format(fmt, name, repoClassName);
				throw new RepositoryInitializationException(msg);
			}
			try {
				repo = repoClass.newInstance();
				cache.put(name, repo);
			}
			catch (InstantiationException | IllegalAccessException e) {
				throw new RepositoryInitializationException(e);
			}
		}
		return repo;
	}

}
