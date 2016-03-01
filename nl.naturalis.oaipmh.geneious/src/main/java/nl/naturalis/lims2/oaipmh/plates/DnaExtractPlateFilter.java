package nl.naturalis.lims2.oaipmh.plates;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPostFilter;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPreFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DnaExtractPlateFilter implements IAnnotatedDocumentPostFilter,
		IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractPlateFilter.class);

	public DnaExtractPlateFilter()
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

	@Override
	public boolean accept(AnnotatedDocument ad)
	{
		return false;
	}

}
