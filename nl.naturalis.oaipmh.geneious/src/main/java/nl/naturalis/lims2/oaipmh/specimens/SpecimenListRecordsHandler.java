package nl.naturalis.lims2.oaipmh.specimens;

import static nl.naturalis.lims2.oaipmh.DocumentNotes.Note.BOLDIDCode_BOLD;
import static nl.naturalis.lims2.oaipmh.DocumentNotes.Note.BOLDURICode_FixedValue;
import static nl.naturalis.lims2.oaipmh.DocumentNotes.Note.NumberOfImagesCode_BOLD;
import static nl.naturalis.lims2.oaipmh.DocumentNotes.Note.RegistrationNumberCode_Samples;

import java.util.ArrayList;
import java.util.List;

import nl.naturalis.lims2.oaipmh.ListRecordsHandler;
import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.DocumentNotes;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPostFilter;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPreFilter;
import nl.naturalis.lims2.oaipmh.jaxb.Geneious;
import nl.naturalis.lims2.oaipmh.jaxb.Specimen;
import nl.naturalis.lims2.oaipmh.jaxb.SpecimenUnit;
import nl.naturalis.oaipmh.api.OAIPMHRequest;

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
