package nl.naturalis.oaipmh.geneious;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Pre-filter shared by all geneious repositories. Filters records from the
 * annotated_document table after they have been converted to
 * {@link AnnotatedDocument} instances by an {@link AnnotatedDocumentFactory}.
 * This class contains course-grained logic for filtering out irrelevant or
 * corrupt records.
 * 
 * @author Ayco Holleman
 *
 */
public class SharedPreFilter implements IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(SharedPreFilter.class);

	private static List<String> acceptableRoots = Arrays.asList("XMLSerialisableRootElement",
			"ABIDocument", "DefaultAlignmentDocument");

	public SharedPreFilter()
	{
	}

	@Override
	public boolean accept(ResultSet rs) throws SQLException
	{
		String xml = rs.getString("document_xml");
		if (rs.wasNull() || xml.trim().isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: document_xml column null or empty");
			}
			return false;
		}
		xml = rs.getString("plugin_document_xml");
		if (rs.wasNull() || (xml = xml.trim()).isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: plugin_document_xml column null or empty");
			}
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
		if (!ok && logger.isDebugEnabled()) {
			logger.debug(
					"Record discarded: <{}> not relevant for OAI-PMH service (see plugin_document_xml column)",
					root);
		}
		return ok;
	}

	private static String getRoot(String xml)
	{
		int x = xml.indexOf(' ');
		int y = xml.indexOf('>');
		x = Math.min(x, y);
		return xml.substring(1, x);
	}
}
