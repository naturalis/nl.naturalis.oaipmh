package nl.naturalis.lims2.oaipmh;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.naturalis.oaipmh.api.CannotDisseminateFormatError;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.ConfigObject;

/**
 * Provides common functionality for LIMS2/Geneious repositories.
 * 
 * @author Ayco Holleman
 *
 */
public class Lims2OAIUtil {

	/**
	 * XML namespace for lims2 elements (http://data.naturalis.nl/geneious).
	 */
	public static final String LIMS2_XMLNS = "http://data.naturalis.nl/geneious";
	/**
	 * XML namespace prefix for lims2 elements ("geneious").
	 */
	public static final String LIMS2_XMLNS_PREFIX = "geneious";

	private static final Logger logger = LogManager.getLogger(Lims2OAIUtil.class);

	private Lims2OAIUtil()
	{
	}

	/**
	 * Make sure metadataPrefix argument is "lims2".
	 * 
	 * @param request
	 * @throws OAIPMHException
	 */
	public static void checkMetadataPrefix(OAIPMHRequest request) throws OAIPMHException
	{
		if (!request.getMetadataPrefix().equals(LIMS2_XMLNS_PREFIX))
			throw new OAIPMHException(new CannotDisseminateFormatError(request));
	}

	/**
	 * Returns a connection to Geneious database.
	 * 
	 * @param cfg
	 * @return
	 * @throws RepositoryException
	 */
	public static Connection connect(ConfigObject cfg) throws RepositoryException
	{
		logger.debug("Connecting to Geneious database");
		try {
			@SuppressWarnings("unused")
			Driver driver = new com.mysql.jdbc.Driver();
			String dsn = cfg.required("db.dsn");
			String user = cfg.required("db.user");
			String password = cfg.required("db.password");
			Connection conn = DriverManager.getConnection(dsn, user, password);
			logger.debug("Connected");
			return conn;
		}
		catch (SQLException e) {
			throw new RepositoryException("Failed to connect to Geneious database", e);
		}
	}

	/**
	 * Closes the specified database connection (null-save).
	 * 
	 * @param conn
	 */
	public static void disconnect(Connection conn)
	{
		if (conn == null)
			return;
		logger.debug("Disconnecting from Geneious database");
		try {
			conn.close();
		}
		catch (SQLException e) {
			logger.error("Error (ignored) while disconnecting from Geneious database", e);
		}
	}

}
