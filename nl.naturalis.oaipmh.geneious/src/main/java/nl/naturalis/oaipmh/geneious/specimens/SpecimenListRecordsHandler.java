package nl.naturalis.oaipmh.geneious.specimens;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.BOLDIDCode_BOLD;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.BOLDURICode_FixedValue;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.NumberOfImagesCode_BOLD;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;

import java.util.ArrayList;
import java.util.List;

import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.geneious.jaxb.Specimen;
import nl.naturalis.oaipmh.geneious.jaxb.SpecimenUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.ConfigObject;

class SpecimenListRecordsHandler extends ListRecordsHandler {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(SpecimenListRecordsHandler.class);

	public SpecimenListRecordsHandler(ConfigObject config, OAIPMHRequest request)
	{
		super(config, request);
	}

	@Override
	protected List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters()
	{
		List<IAnnotatedDocumentPreFilter> filters = new ArrayList<>(1);
		filters.add(new SpecimenFilter());
		return filters;
	}

	@Override
	protected List<IAnnotatedDocumentPostFilter> getAnnotatedDocumentPostFilters()
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
		unit.setAssociatedUnitID(notes.get(BOLDIDCode_BOLD));
		unit.setUri(notes.get(BOLDURICode_FixedValue));
		String s = notes.get(NumberOfImagesCode_BOLD);
		if (s != null) {
			Integer i = Integer.valueOf(s);
			unit.setMultiMediaObjectComment(i);
		}
	}

	@Override
	protected int getPageSize()
	{
		return config.getInt("specimens.repo.pagesize");
	}

}
