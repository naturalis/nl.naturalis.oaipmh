package nl.naturalis.oaipmh.geneious.plates;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openarchives.oai._2.HeaderType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.MetadataType;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.RecordType;

import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;
import nl.naturalis.oaipmh.geneious.GeneiousOAIUtil;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostProcessor;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.jaxb.DnaExtractPlate;
import nl.naturalis.oaipmh.geneious.jaxb.ExtractPlateUnit;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.util.ConfigObject;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormatter;
import static nl.naturalis.oaipmh.api.util.ObjectFactories.oaiFactory;

/**
 * Handles ListRecords requests for extract plates.
 * 
 * @author Ayco Holleman
 *
 */
class DnaExtractPlateListRecordsHandler extends ListRecordsHandler {

	public DnaExtractPlateListRecordsHandler(ConfigObject config, OAIPMHRequest request)
	{
		super(config, request);
	}

	@Override
	protected List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters()
	{
		List<IAnnotatedDocumentPreFilter> filters = new ArrayList<>(1);
		filters.add(new DnaExtractPlatePreFilter());
		return filters;
	}

	@Override
	protected List<IAnnotatedDocumentPostFilter> getAnnotatedDocumentPostFilters()
	{
		return new ArrayList<>(0);
	}

	@Override
	protected List<IAnnotatedDocumentSetFilter> getAnnotatedDocumentSetFilters()
	{
		List<IAnnotatedDocumentSetFilter> filters = new ArrayList<>(1);
		filters.add(new DnaExtractPlateSetFilter());
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
		geneious.setDnaExtractPlate(createDnaExtractPlate(ad));
	}

	private static DnaExtractPlate createDnaExtractPlate(AnnotatedDocument ad)
	{
		DnaExtractPlate plate = new DnaExtractPlate();
		ExtractPlateUnit unit = new ExtractPlateUnit();
		DocumentNotes notes = ad.getDocument().getNotes();
		unit.setInstitutePlateID(notes.get(Note.ExtractPlateNumberCode_Samples));
		plate.setUnit(unit);
		return plate;
	}

	@Override
	protected int getPageSize()
	{
		return config.getInt("dna-extract-plates.repo.pagesize");
	}

	@SuppressWarnings("static-method")
	OAIPMHtype handleRequest_old(OAIPMHRequest request) throws OAIPMHException
	{
		GeneiousOAIUtil.checkRequest(request);
		OAIPMHtype root = createResponseSkeleton(request);
		ListRecordsType listRecords = oaiFactory.createListRecordsType();
		root.setListRecords(listRecords);
		RecordType record = oaiFactory.createRecordType();
		listRecords.getRecord().add(record);

		HeaderType header = oaiFactory.createHeaderType();
		record.setHeader(header);
		header.setIdentifier("123423046");
		header.setDatestamp(dateTimeFormatter.format(new Date()));

		MetadataType metadata = oaiFactory.createMetadataType();
		record.setMetadata(metadata);
		Geneious geneious = new Geneious();
		metadata.setAny(geneious);
		DnaExtractPlate slide = new DnaExtractPlate();
		geneious.setDnaExtractPlate(slide);
		ExtractPlateUnit unit = new ExtractPlateUnit();
		slide.setUnit(unit);
		unit.setInstitutePlateID("NBCN123456");

		return root;
	}

}
