package nl.naturalis.oaipmh.geneious.plates;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentPreFilter pre-filter} for DNA extract plates. This
 * filter filters out any record that does not contain the string
 * "<ExtractPlateNumberCode_Samples>" in its document_xml column.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractPlatePreFilter implements IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractPlatePreFilter.class);

	public DnaExtractPlatePreFilter()
	{
	}

	@Override
	public boolean accept(ResultSet rs) throws SQLException
	{
		// Some bare-knuckle XML parsing here for fail-fast processing
		String xml = rs.getString("document_xml");
		if (xml.indexOf("<ExtractPlateNumberCode_Samples>") == -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: document_xml column does not contain string \"<ExtractPlateNumberCode_Samples>\"");
			}
			return false;
		}
		return true;
	}

}
