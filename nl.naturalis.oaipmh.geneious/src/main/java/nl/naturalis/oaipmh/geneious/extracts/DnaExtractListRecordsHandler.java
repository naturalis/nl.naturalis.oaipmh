package nl.naturalis.oaipmh.geneious.extracts;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormatter;
import static nl.naturalis.oaipmh.api.util.ObjectFactories.oaiFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.GeneiousOAIUtil;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.XMLSerialisableRootElement;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;
import nl.naturalis.oaipmh.geneious.jaxb.Amplification;
import nl.naturalis.oaipmh.geneious.jaxb.DnaExtract;
import nl.naturalis.oaipmh.geneious.jaxb.DnaLabProject;
import nl.naturalis.oaipmh.geneious.jaxb.ExtractUnit;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.geneious.jaxb.GeneticAccession;
import nl.naturalis.oaipmh.geneious.jaxb.Sequencing;

import org.domainobject.util.ConfigObject;
import org.openarchives.oai._2.HeaderType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.MetadataType;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.RecordType;

/**
 * Handles ListRecords requests for DNA extracts.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractListRecordsHandler extends ListRecordsHandler {

	public DnaExtractListRecordsHandler(ConfigObject config, OAIPMHRequest request)
	{
		super(config, request);
	}

	@Override
	protected List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters()
	{
		List<IAnnotatedDocumentPreFilter> filters = new ArrayList<>(1);
		filters.add(new DnaExtractFilter());
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
		geneious.setDnaExtract(createDnaExtract(ad));
	}

	@Override
	protected int getPageSize()
	{
		return config.getInt("dna-extracts.repo.pagesize");
	}

	private static DnaExtract createDnaExtract(AnnotatedDocument ad)
	{
		DnaExtract extract = new DnaExtract();
		extract.setUnit(createExtractUnit(ad));
		extract.setDnaLabProject(createDnaLabProject(ad));
		return extract;
	}

	private static DnaLabProject createDnaLabProject(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		DnaLabProject project = new DnaLabProject();
		project.setBatchID(notes.get(Note.ProjectPlateNumberCode_Samples));
		project.setVersionNumber(notes.get(Note.DocumentVersionCode_Seq));
		project.setSequencing(createSequencing(ad));
		project.setAmplification(createAmplification(ad));
		return project;
	}

	private static ExtractUnit createExtractUnit(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		ExtractUnit unit = new ExtractUnit();
		unit.setUnitID(notes.get(Note.ExtractIDCode_Samples));
		unit.setAssociatedUnitID(notes.get(Note.RegistrationNumberCode_Samples));
		unit.setInstitutePlateID(notes.get(Note.ExtractPlateNumberCode_Samples));
		unit.setPlatePosition(notes.get(Note.PlatePositionCode_Samples));
		return unit;
	}

	private static Sequencing createSequencing(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		Sequencing seq = new Sequencing();
		seq.setSequencingStaff(notes.get(Note.SequencingStaffCode_FixedValue_Samples));
		seq.setAmplificationStaff(notes.get(Note.AmplicificationStaffCode_FixedValue_Seq));
		if (ad.getPluginDocument() instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement e = (XMLSerialisableRootElement) ad.getPluginDocument();
			seq.setConsensusSequenceID(e.getName());
		}
		seq.setConsensusSequenceLength(notes.get(Note.NucleotideLengthCode_Bold));
		seq.setConsensusSequenceQuality("???? (to be determined)");
		seq.setGeneticAccession(createGeneticAccession(ad));
		return seq;
	}

	private static GeneticAccession createGeneticAccession(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		GeneticAccession ga = new GeneticAccession();
		ga.setBOLDProcessID(notes.get(Note.BOLDIDCode_Bold));
		ga.setGeneticAccessionNumber(notes.get(Note.GenBankIDCode_Bold));
		ga.setGeneticAccessionNumberURI(notes.get(Note.GenBankURICode_FixedValue_Bold));
		return ga;
	}

	private static Amplification createAmplification(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		Amplification amp = new Amplification();
		amp.setAmplificationStaff(notes.get(Note.AmplicificationStaffCode_FixedValue_Seq));
		amp.setMarker(notes.get(Note.MarkerCode_Seq));
		return amp;
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

		DnaExtract extract = new DnaExtract();
		geneious.setDnaExtract(extract);
		ExtractUnit unit = new ExtractUnit();
		extract.setUnit(unit);
		unit.setUnitID("e123214324");
		unit.setAssociatedUnitID("RMNH.INS.23917");
		unit.setInstitutePlateID("NBCN123456");
		unit.setPlatePosition("A10");
		DnaLabProject project = new DnaLabProject();
		extract.setDnaLabProject(project);
		project.setBatchID("BCP1234");
		Sequencing seq = new Sequencing();
		project.setSequencing(seq);
		seq.setSequencingStaff("");
		seq.setAmplificationStaff("");
		seq.setConsensusSequenceID("e4010125106_Rhy_ger_MJ243_COI-H08_M13R_P15_025");
		seq.setConsensusSequenceLength("650");
		seq.setConsensusSequenceQuality("fault");
		GeneticAccession ga = new GeneticAccession();
		seq.setGeneticAccession(ga);
		ga.setBOLDProcessID("ANTVI001-11");
		ga.setGeneticAccessionNumber("JQ412562");
		ga.setGeneticAccessionNumberURI("http://www.ncbi.nlm.nih.gov/nuccore/JQ412562");
		Amplification amp = new Amplification();
		project.setAmplification(amp);
		amp.setAmplificationStaff("");
		amp.setMarker("CO1");

		return root;
	}

}
