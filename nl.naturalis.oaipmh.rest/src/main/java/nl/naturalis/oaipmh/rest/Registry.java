package nl.naturalis.oaipmh.rest;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.ConfigObject;
import org.domainobject.util.FileUtil;

/**
 * Class responsible for configuring the REST service.
 * 
 * @author Ayco Holleman
 *
 */
public class Registry {

	/**
	 * Name of the central configuration file for the OAIPMH REST service
	 * (oaipmh.properties).
	 */
	static final String CONFIG_FILE_NAME = "oaipmh.properties";

	/*
	 * System property that tells us where the configuration directory
	 * (containing purl.properties) is. When using Wildfly, this system property
	 * is probably set in standalone.xml
	 */
	private static final String SYSPROP_CONFIG_DIR = "nl.naturalis.oaipmh.conf.dir";

	private static final Logger logger = LogManager.getLogger(Registry.class);

	private static Registry instance;

	/**
	 * Instantiates and initializes a {@code Registry} instance. This method
	 * must be called before servicing any request. If anything goes wrong while
	 * initializing the {@code Registry}, an
	 * {@link ApplicationInitializationException} is thrown and the application
	 * dies already during startup. An explanation of what went wrong is written
	 * to the Wildfly log (standalone/log/server.log).
	 */
	public static void initialize()
	{
		if (instance == null) {
			instance = new Registry();
		}
	}

	/**
	 * Returns a {@code Registry} instance. Will call {@link #initialize()}
	 * first.
	 * 
	 * @return A {@code Registry} instance.
	 */
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
	 * Get a {@link ConfigObject} for the main configuration file
	 * (oaipmh-rest.properties).
	 * 
	 * @return
	 */
	public ConfigObject getConfig()
	{
		return config;
	}

	/**
	 * Get the directory designated to contain the application's configuration
	 * files. This directory will contain at least purl.properties, but may
	 * contain additional files that the application expects to be there.
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
			String fmt = "Invalid value for system property \"%s\": \"%s\" (no such directory)";
			String msg = String.format(fmt, SYSPROP_CONFIG_DIR, path);
			throw new ApplicationInitializationException(msg);
		}
		try {
			confDir = dir.getCanonicalFile();
			if (logger.isDebugEnabled()) {
				String p = dir.getAbsolutePath();
				logger.debug("Configuration directory for this application: " + p);
			}
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
		if (logger.isDebugEnabled())
			logger.debug("Loading application configuration from " + file.getAbsolutePath());
		this.config = new ConfigObject(file);
	}
}
