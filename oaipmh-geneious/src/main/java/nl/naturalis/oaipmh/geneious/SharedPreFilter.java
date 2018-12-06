package nl.naturalis.oaipmh.geneious;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentPreFilter Pre-filter} shared by all geneious
 * repositories. This class contains course-grained logic for filtering out
 * irrelevant or corrupt records.
 * <ul>
 * <li>The document_xml column is not null or empty
 * <li>The plugin_document_xml column is not null or empty
 * <li>The document root of the XML within the plugin_document_xml column is one
 * of &lt;XMLSerialisableRootElement&gt;, &lt;ABIDocument&gt;,
 * &lt;DefaultAlignmentDocument&gt;
 * </ul>
 * 
 * @author Ayco Holleman
 *
 */
public class SharedPreFilter implements IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(SharedPreFilter.class);

	private static List<String> acceptableRoots = Arrays.asList("XMLSerialisableRootElement",
			"DefaultAlignmentDocument");

	private int numAccepted;
	private int numDiscarded;

	public SharedPreFilter()
	{
	}

	@Override
	public boolean accept(ResultSet rs) throws SQLException
	{
		String xml = rs.getString("document_xml");
		if (rs.wasNull() || xml.trim().isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("document_xml column null or empty");
			}
			++numDiscarded;
			return false;
		}
		xml = rs.getString("plugin_document_xml");
		if (rs.wasNull() || (xml = xml.trim()).isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("plugin_document_xml column null or empty");
			}
			++numDiscarded;
			return false;
		}
		String root = getRoot(xml);
		boolean ok = false;
		for (String acceptable : acceptableRoots) {
			ok = ok || root.equals(acceptable);
			if (ok) {
				break;
			}
		}
		if (ok) {
			++numAccepted;
			return true;
		}
		if (logger.isDebugEnabled()) {
			discard("Unusable XML in plugin_document_xml: <{}>", root);
		}
		++numDiscarded;
		return false;
	}

	@Override
	public int getNumAccepted()
	{
		return numAccepted;
	}

	@Override
	public int getNumDiscarded()
	{
		return numDiscarded;
	}

	private static String getRoot(String xml)
	{
		int x = xml.indexOf(' ');
		int y = xml.indexOf('>');
		x = Math.min(x, y);
		return xml.substring(1, x);
	}

	private static void discard(String msg, Object... args)
	{
		logger.debug("Record discarded. " + msg, args);
	}
}
