package nl.naturalis.oaipmh.geneious.specimens;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;

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
		return true;
	}

}
