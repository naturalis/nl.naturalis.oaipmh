package nl.naturalis.lims2.oaipmh.extracts;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPostFilter;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPreFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements both pre and post filtering for DNA extracts. Only the method
 * specified by {@link IAnnotatedDocumentPreFilter} actually contains filter
 * logic. The method specified by {@link IAnnotatedDocumentPostFilter} currently
 * just returns {@code true}.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractFilter implements IAnnotatedDocumentPostFilter, IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractFilter.class);
	private static final String[] SEARCH_STRINGS = new String[] { "<ExtractIDCode_Samples>" };

	public DnaExtractFilter()
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
					String fmt = "Record discarded: missing \"{}\" in document_xml";
					logger.debug(fmt, s);
				}
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean accept(AnnotatedDocument ad)
	{
		return true;
	}

}
