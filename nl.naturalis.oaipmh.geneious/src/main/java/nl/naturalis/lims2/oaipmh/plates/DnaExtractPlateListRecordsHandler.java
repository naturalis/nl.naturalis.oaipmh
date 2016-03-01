package nl.naturalis.lims2.oaipmh.plates;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormatter;
import static nl.naturalis.oaipmh.api.util.ObjectFactories.oaiFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.DocumentNotes;
import nl.naturalis.lims2.oaipmh.DocumentNotes.Note;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPostFilter;
import nl.naturalis.lims2.oaipmh.IAnnotatedDocumentPreFilter;
import nl.naturalis.lims2.oaipmh.Lims2OAIUtil;
import nl.naturalis.lims2.oaipmh.ListRecordsHandler;
import nl.naturalis.lims2.oaipmh.jaxb.DnaExtractPlate;
import nl.naturalis.lims2.oaipmh.jaxb.ExtractPlateUnit;
import nl.naturalis.lims2.oaipmh.jaxb.Geneious;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;

import org.domainobject.util.ConfigObject;
import org.openarchives.oai._2.HeaderType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.MetadataType;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.RecordType;

class DnaExtractPlateListRecordsHandler extends ListRecordsHandler {

	public DnaExtractPlateListRecordsHandler(ConfigObject config, OAIPMHRequest request)
	{
		super(config, request);
	}

	@Override
	protected List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters()
	{
		List<IAnnotatedDocumentPreFilter> filters = new ArrayList<>(1);
		filters.add(new DnaExtractPlateFilter());
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
		Lims2OAIUtil.checkMetadataPrefix(request);
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
