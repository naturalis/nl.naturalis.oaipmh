package nl.naturalis.oaipmh;

import java.io.File;
import java.io.IOException;

import org.domainobject.util.ConfigObject;
import org.domainobject.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Registry {

	public static void main(String[] args)
	{
		System.out.println(Registry.class.getResource(""));
	}

	/*
	 * System property that tells us where the configuration directory (containing
	 * purl.properties) is. When using Wildfly, this system property is probably set in
	 * standalone.xml
	 */
	private static final String SYSPROP_CONFIG_DIR = "nl.naturalis.oaipmh.conf.dir";
	/*
	 * Name of the central configuration file for the OAIPMH REST service.
	 */
	private static final String CONFIG_FILE_NAME = "oaipmh.properties";

	private static final Logger logger = LoggerFactory.getLogger(Registry.class);

	private static Registry instance;

	public static void initialize()
	{
		if (instance == null) {
			instance = new Registry();
		}
	}

	public static Registry getInstance()
	{
		initialize();
		return instance;
	}

	private File confDir;
	private ConfigObject config;

	private Registry()
	{
		setConfDir();
		loadConfig();
	}

	/**
	 * Get a {@link ConfigObject} for the main configuration file (purl.properties).
	 * 
	 * @return
	 */
	public ConfigObject getConfig()
	{
		return config;
	}

	/**
	 * Get the directory designated to contain the application's configuration files. This
	 * directory will contain at least purl.properties, but may contain additional files
	 * that the application expects to be there.
	 * 
	 * @return
	 */
	public File getConfDir()
	{
		return confDir;
	}

	private void setConfDir()
	{
		String path = System.getProperty(SYSPROP_CONFIG_DIR);
		if (path == null) {
			String msg = String.format("Missing system property \"%s\"", SYSPROP_CONFIG_DIR);
			throw new ApplicationInitializationException(msg);
		}
		File dir = new File(path);
		if (!dir.isDirectory()) {
			String msg = String.format(
					"Invalid value for system property \"%s\": \"%s\" (no such directory)",
					SYSPROP_CONFIG_DIR, path);
			throw new ApplicationInitializationException(msg);
		}
		try {
			confDir = dir.getCanonicalFile();
			logger.info("Configuration directory for this application: " + dir.getAbsolutePath());
		}
		catch (IOException e) {
			throw new ApplicationInitializationException(e);
		}
	}

	private void loadConfig()
	{
		File file = FileUtil.newFile(confDir, CONFIG_FILE_NAME);
		if (!file.isFile()) {
			String msg = String.format("Configuration file missing: %s", file.getPath());
			throw new ApplicationInitializationException(msg);
		}
		logger.info("Loading application configuration from " + file.getAbsolutePath());
		this.config = new ConfigObject(file);
	}
}
