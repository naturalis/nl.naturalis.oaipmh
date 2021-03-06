package nl.naturalis.oaipmh.geneious.extracts;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentPreFilter pre-filter} for DNA extracts. This
 * filter filters out any record that does not contain the string
 * "&lt;ExtractIDCode_Samples&gt;" in its document_xml column.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractPreFilter implements IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractPreFilter.class);
	private static final String[] SEARCH_STRINGS = new String[] { "<ExtractIDCode_Samples>" };

	private int numAccepted;
	private int numDiscarded;

	public DnaExtractPreFilter()
	{
	}

	@Override
	public boolean accept(ResultSet rs) throws SQLException
	{
		// Some bare-knuckle XML parsing here for fail-fast processing
		String xml = rs.getString("document_xml");
		for (String s : SEARCH_STRINGS) {
			if (xml.indexOf(s) == -1) {
				if (logger.isDebugEnabled()) {
					String fmt = "Record discarded. Missing required element {} in document_xml";
					logger.debug(fmt, s);
				}
				++numDiscarded;
				return false;
			}
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
