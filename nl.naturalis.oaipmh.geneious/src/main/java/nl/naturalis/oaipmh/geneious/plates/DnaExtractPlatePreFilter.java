package nl.naturalis.oaipmh.geneious.plates;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentPreFilter pre-filter} for DNA extract plates. This
 * filter filters out any record that does not contain the string
 * "&lt;ExtractPlateNumberCode_Samples&gt;" in its document_xml column.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractPlatePreFilter implements IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractPlatePreFilter.class);

	private int numAccepted;
	private int numDiscarded;

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
				String msg = "Record discarded. Missing required element "
						+ "<ExtractPlateNumberCode_Samples> in document_xml";
				logger.debug(msg);
			}
			++numDiscarded;
			return false;
		}
		++numAccepted;
		return true;
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
}
