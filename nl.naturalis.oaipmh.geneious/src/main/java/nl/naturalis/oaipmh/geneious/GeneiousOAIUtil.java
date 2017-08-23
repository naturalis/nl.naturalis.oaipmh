package nl.naturalis.oaipmh.geneious;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.naturalis.oaipmh.api.CannotDisseminateFormatError;
import nl.naturalis.oaipmh.api.NoSetHierarchyError;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.ConfigObject;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.*;

/**
 * Provides common functionality for Geneious OAI repositories.
 * 
 * @author Ayco Holleman
 *
 */
public class GeneiousOAIUtil {

	/**
	 * XML namespace for geneious elements (http://data.naturalis.nl/geneious).
	 */
	public static final String GENEIOUS_XMLNS = "http://data.naturalis.nl/geneious";
	/**
	 * XML namespace prefix for geneious elements ("geneious").
	 */
	public static final String GENEIOUS_XMLNS_PREFIX = "geneious";

	private static final Logger logger = LogManager.getLogger(GeneiousOAIUtil.class);

	private GeneiousOAIUtil()
	{
	}

	/**
	 * Make sure metadataPrefix argument is "geneious".
	 * 
	 * @param request
	 * @throws OAIPMHException
	 */
	public static void checkRequest(OAIPMHRequest request) throws OAIPMHException
	{
		if (!request.getMetadataPrefix().equals(GENEIOUS_XMLNS_PREFIX))
			throw new OAIPMHException(new CannotDisseminateFormatError(request));
		if (request.getSet() != null)
			throw new OAIPMHException(new NoSetHierarchyError(request));
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
		String dsn = cfg.required("db.dsn");
		logger.info("Connecting to Geneious database: " + dsn);
		String user = cfg.required("db.user");
		String password = cfg.required("db.password");
		try {
			@SuppressWarnings("unused")
			Driver driver = new com.mysql.jdbc.Driver();
			Connection conn = DriverManager.getConnection(dsn, user, password);
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
		if (logger.isDebugEnabled())
			logger.debug("Disconnecting from Geneious database");
		try {
			conn.close();
		}
		catch (SQLException e) {
			logger.error("Error (ignored) while disconnecting from Geneious database", e);
			conn = null;
		}
	}

	public static int getDocumentMaturity(AnnotatedDocument ad)
	{
		if (isConsensus(ad))
			return 10;
		if (isContig(ad))
			return 9;
		if (isFasta(ad))
			return 8;
		if (isAb1(ad))
			return 7;
		if (isAb1Reversed(ad))
			return 6;
		if (isDummy(ad))
			return 5;
		String msg = "Cannot determine maturity of annotated_document record with id " + ad.getId();
		logger.error(msg);
		throw new RuntimeException(msg);
	}

	/**
	 * Returns whether or not the specified document represents a fasta document
	 * (a&#46;k&#46;a&#46; fasta record). This is considered to be the case if:
	 * <ol>
	 * <li>The document_xml column contains a &lt;note&gt; element containing a
	 * &lt;filename&gt; whose value ends with ".fas"
	 * </ol>
	 * 
	 * @param ad
	 * @return
	 */
	public static boolean isFasta(AnnotatedDocument ad)
	{
		String fileName = ad.getDocument().getNote(filename);
		return fileName != null && fileName.endsWith(".fas");
	}

	/**
	 * Returns whether or not the specified document represents a contig
	 * document (a&#46;k&#46;a&#46; contig record). This is considered to be the
	 * case if:
	 * <ol>
	 * <li>The plugin_xml column has a &lt;DefaultAlignmentDocument&gt; root
	 * element
	 * <li>The {@code isContig} attribute of the root element is set to "true"
	 * </ol>
	 * 
	 * @param ad
	 * @return
	 */
	public static boolean isContig(AnnotatedDocument ad)
	{
		if (ad.getPluginDocument() instanceof DefaultAlignmentDocument) {
			DefaultAlignmentDocument dad = (DefaultAlignmentDocument) ad.getPluginDocument();
			return dad.isContig() != null && dad.isContig();
		}
		return false;
	}

	/**
	 * Returns whether or not the specified document represents a consensus
	 * document (a&#46;k&#46;a&#46; consensus record). This is considered to be
	 * the case if:
	 * <ol>
	 * <li>The plugin_xml column has a &lt;XMLSerialisableRootElement&gt; root
	 * element
	 * <li>The XML in plugin_xml contains a &lt;name&gt; whose value ends with
	 * "consensus sequence". In other words whatever name the Geneious user
	 * wants to give to the consensus sequence record, the last two words
	 * <b>must</b> be "consensus sequence" (case sensitive), otherwise this
	 * module will not be able to identify consensus sequence records and the
	 * OAI-PMH it generates will not be correct!
	 * </ol>
	 * 
	 * @param ad
	 * @return
	 */
	public static boolean isConsensus(AnnotatedDocument ad)
	{
		PluginDocument pd = ad.getPluginDocument();
		if (pd instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement xsre = (XMLSerialisableRootElement) pd;
			String name = xsre.getName();
			return name != null && name.endsWith("consensus sequence");
		}
		return false;
	}

	/**
	 * Returns whether or not the specified document is an AB1 document.
	 * 
	 * @param ad
	 * @return
	 */
	public static boolean isAb1(AnnotatedDocument ad)
	{
		PluginDocument pd = ad.getPluginDocument();
		if (pd instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement xsre = (XMLSerialisableRootElement) pd;
			String name = xsre.getName();
			return name != null && name.endsWith(".ab1");
		}
		return false;
	}

	/**
	 * Returns whether or not the specified document is an reversed AB1
	 * document.
	 * 
	 * @param ad
	 * @return
	 */
	public static boolean isAb1Reversed(AnnotatedDocument ad)
	{
		PluginDocument pd = ad.getPluginDocument();
		if (pd instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement xsre = (XMLSerialisableRootElement) pd;
			String name = xsre.getName();
			return name != null && name.endsWith(".ab1 (reversed)");
		}

		return false;
	}

	/**
	 * Returns whether or not the specified document is an AB1 document.
	 * 
	 * @param ad
	 * @return
	 */
	public static boolean isDummy(AnnotatedDocument ad)
	{
		PluginDocument pd = ad.getPluginDocument();
		if (pd instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement xsre = (XMLSerialisableRootElement) pd;
			String name = xsre.getName();
			return name != null && name.endsWith(".dum");
		}
		return false;
	}

}
