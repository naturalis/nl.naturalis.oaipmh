package nl.naturalis.oaipmh.geneious.specimens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostProcessor;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.geneious.jaxb.Specimen;
import nl.naturalis.oaipmh.geneious.jaxb.SpecimenUnit;
import nl.naturalis.oaipmh.util.ConfigObject;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.BOLDIDCode_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.BOLDURICode_FixedValue_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.NumberOfImagesCode_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;

/**
 * Handles ListRecords requests for specimens.
 * 
 * @author Ayco Holleman
 *
 */
class SpecimenListRecordsHandler extends ListRecordsHandler {

	private static final Logger logger = LogManager.getLogger(SpecimenListRecordsHandler.class);

	public SpecimenListRecordsHandler(ConfigObject config, OAIPMHRequest request)
	{
		super(config, request);
	}

	@Override
	protected List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters()
	{
		return Collections.emptyList();
	}

	@Override
	protected List<IAnnotatedDocumentPostFilter> getAnnotatedDocumentPostFilters()
	{
		List<IAnnotatedDocumentPostFilter> filters = new ArrayList<>(1);
		filters.add(new SpecimenPostFilter());
		return filters;
	}

	@Override
	protected List<IAnnotatedDocumentSetFilter> getAnnotatedDocumentSetFilters()
	{
		List<IAnnotatedDocumentSetFilter> filters = new ArrayList<>(1);
		filters.add(new SpecimenSetFilter());
		return filters;
	}

	@Override
	protected List<IAnnotatedDocumentPostProcessor> getAnnotatedDocumentPostProcessors()
	{
		return new ArrayList<>(0);
	}

	@Override
	protected void setMetadata(Geneious geneious, AnnotatedDocument ad)
	{
		Specimen specimen = new Specimen();
		geneious.setSpecimen(specimen);
		SpecimenUnit unit = new SpecimenUnit();
		specimen.setUnit(unit);
		DocumentNotes notes = ad.getDocument().getNotes();
		unit.setUnitID(notes.get(RegistrationNumberCode_Samples));
		unit.setAssociatedUnitID(notes.get(BOLDIDCode_Bold));
		unit.setUri(notes.get(BOLDURICode_FixedValue_Bold));
		String s = notes.get(NumberOfImagesCode_Bold);
		if (s != null) {
			try {
				Integer i = Integer.valueOf(s);
				unit.setMultiMediaObjectComment(i);
			}
			catch (NumberFormatException e) {
				logger.warn("Bad number for NumberOfImagesCode_Bold: {}", s);
			}
		}
	}

	@Override
	protected int getPageSize()
	{
		return config.getInt("specimens.repo.pagesize");
	}

}
