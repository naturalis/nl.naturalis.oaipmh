package nl.naturalis.oaipmh;

import java.util.HashMap;

import nl.naturalis.oaipmh.api.IRepository;

import org.domainobject.util.ConfigObject;

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

	@SuppressWarnings("unchecked")
	public IRepository create(String name) throws RepositoryInitializationException
	{
		IRepository repo = cache.get(name);
		if (repo == null) {
			ConfigObject cfg = Registry.getInstance().getConfig();
			String property = "repository." + name + ".class";
			String value = cfg.get(property);
			if (value == null)
				throw new RepositoryInitializationException("No such repository: " + name);
			Class<IRepository> repoClass;
			try {
				repoClass = (Class<IRepository>) Class.forName(value);
			}
			catch (ClassNotFoundException e) {
				String fmt = "Missing implementation for repository %s (%s)";
				String msg = String.format(fmt, name, value);
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
