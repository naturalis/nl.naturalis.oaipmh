package nl.naturalis.oaipmh.geneious.extracts;

import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.cache_name;
import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.override_cache_name;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.AmplicificationStaffCode_FixedValue_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.BOLDIDCode_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ConsensusSeqPassCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.GenBankIDCode_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.GenBankURICode_FixedValue_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.NucleotideLengthCode_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.PCRplateIDCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.PlatePositionCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ProjectPlateNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.SequencingStaffCode_FixedValue_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.filename;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isConsensus;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isContig;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isFasta;

import java.util.ArrayList;
import java.util.List;

import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostProcessor;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.jaxb.Amplification;
import nl.naturalis.oaipmh.geneious.jaxb.DnaExtract;
import nl.naturalis.oaipmh.geneious.jaxb.DnaLabProject;
import nl.naturalis.oaipmh.geneious.jaxb.ExtractUnit;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.geneious.jaxb.GeneticAccession;
import nl.naturalis.oaipmh.geneious.jaxb.Sequencing;

import org.domainobject.util.ConfigObject;

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
		filters.add(new DnaExtractPreFilter());
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
		filters.add(new DnaExtractSetFilter());
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
		project.setBatchID(notes.get(ProjectPlateNumberCode_Samples));
		project.setVersionNumber(notes.get(DocumentVersionCode_Seq));
		project.setSequencing(createSequencing(ad));
		project.setAmplification(createAmplification(ad));
		return project;
	}

	private static ExtractUnit createExtractUnit(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		ExtractUnit unit = new ExtractUnit();
		unit.setUnitID(notes.get(ExtractIDCode_Samples));
		unit.setAssociatedUnitID(notes.get(RegistrationNumberCode_Samples));
		unit.setInstitutePlateID(notes.get(ExtractPlateNumberCode_Samples));
		unit.setPlatePosition(notes.get(PlatePositionCode_Samples));
		return unit;
	}

	private static Sequencing createSequencing(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		Sequencing sequencing = new Sequencing();
		sequencing.setSequencingStaff(notes.get(SequencingStaffCode_FixedValue_Seq));
		sequencing.setGeneticAccession(createGeneticAccession(ad));
		sequencing.setConsensusSequenceQuality(notes.get(ConsensusSeqPassCode_Seq));
		if (isFasta(ad)) {
			String csi = ad.getDocument().getHiddenField(override_cache_name);
			if (csi == null) {
				csi = ad.getDocument().getNote(filename);
			}
			String csl = notes.get(NucleotideLengthCode_Bold);
			sequencing.setConsensusSequenceID(csi);
			sequencing.setConsensusSequenceLength(csl);
		}
		else if (isContig(ad)) {
			String csi = ad.getDocument().getHiddenField(override_cache_name);
			String csl = notes.get(NucleotideLengthCode_Bold);
			sequencing.setConsensusSequenceID(csi);
			sequencing.setConsensusSequenceLength(csl);
		}
		else if (isConsensus(ad)) {
			String csi = ad.getDocument().getHiddenField(override_cache_name);
			if (csi == null) {
				csi = ad.getDocument().getHiddenField(cache_name);
			}
			String csl = notes.get(NucleotideLengthCode_Bold);
			sequencing.setConsensusSequenceID(csi);
			sequencing.setConsensusSequenceLength(csl);
		}
		else {
			sequencing.setConsensusSequenceID(null);
			sequencing.setConsensusSequenceLength(null);
			sequencing.setConsensusSequenceQuality(null);
		}
		return sequencing;
	}

	private static GeneticAccession createGeneticAccession(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		GeneticAccession ga = new GeneticAccession();
		ga.setBOLDProcessID(notes.get(BOLDIDCode_Bold));
		ga.setGeneticAccessionNumber(notes.get(GenBankIDCode_Bold));
		ga.setGeneticAccessionNumberURI(notes.get(GenBankURICode_FixedValue_Bold));
		return ga;
	}

	private static Amplification createAmplification(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		Amplification amp = new Amplification();
		amp.setAmplificationStaff(notes.get(AmplicificationStaffCode_FixedValue_Samples));
		amp.setMarker(notes.get(MarkerCode_Seq));
		amp.setPcrPlateID(notes.get(PCRplateIDCode_Seq));
		return amp;
	}

}
