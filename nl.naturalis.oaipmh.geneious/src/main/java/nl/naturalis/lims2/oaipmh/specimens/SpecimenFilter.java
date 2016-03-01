package nl.naturalis.lims2.oaipmh.specimens;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.Document;
import nl.naturalis.lims2.oaipmh.DocumentNotes.Note;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPostFilter;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPreFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpecimenFilter implements IAnnotatedDocumentPostFilter, IAnnotatedDocumentPreFilter {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(SpecimenFilter.class);

	public SpecimenFilter()
	{
	}

	@Override
	public boolean accept(ResultSet rs) throws SQLException
	{
		return true;
	}

	@Override
	public boolean accept(AnnotatedDocument ad)
	{
		Document d = ad.getDocument();
		if (d == null)
			return false;
		if (d.getNotes() == null)
			return false;
		return "true".equals(d.getNotes().get(Note.CRSCode_CRS));
	}

}
