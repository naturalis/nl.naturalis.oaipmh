package nl.naturalis.oaipmh.geneious.extracts;

import static nl.naturalis.oaipmh.geneious.DocumentFields.Field.consensusSequenceLength;
import static nl.naturalis.oaipmh.geneious.DocumentFields.Field.sequence_length;
import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.cache_name;
import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.override_cache_name;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.filename;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isConsensus;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isContig;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isFasta;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostProcessor;
import nl.naturalis.oaipmh.geneious.PostProcessingException;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.geneious.jaxb.Sequencing;

import org.openarchives.oai._2.RecordType;

/**
 * A post processor for DNA extracts. Manipulates {@link AnnotatedDocument}
 * according to whether they represent fast, contig or consensus records.
 * 
 * @deprecated This is now simply handled within
 *             {@link DnaExtractListRecordsHandler}.
 * 
 * @author Ayco Holleman
 *
 */
@Deprecated
public class DnaExtractPostProcessor implements IAnnotatedDocumentPostProcessor {

	public DnaExtractPostProcessor()
	{
	}

	@Override
	public void process(RecordType record, AnnotatedDocument annotatedDocument)
			throws PostProcessingException
	{
		if (isFasta(annotatedDocument)) {
			processFasta(record, annotatedDocument);
		}
		else if (isContig(annotatedDocument)) {
			processContig(record, annotatedDocument);
		}
		else if (isConsensus(annotatedDocument)) {
			processConsensus(record, annotatedDocument);
		}
		else {
			processOther(record, annotatedDocument);
		}
	}

	private static void processFasta(RecordType record, AnnotatedDocument ad)
	{
		String csi = ad.getDocument().getNote(filename);
		String csl = ad.getDocument().getField(sequence_length);
		Geneious geneious = (Geneious) record.getMetadata().getAny();
		Sequencing sequencing = geneious.getDnaExtract().getDnaLabProject().getSequencing();
		sequencing.setConsensusSequenceID(csi);
		sequencing.setConsensusSequenceLength(csl);
	}

	private static void processContig(RecordType record, AnnotatedDocument ad)
	{
		String csi = ad.getDocument().getHiddenField(override_cache_name);
		String csl = ad.getDocument().getField(consensusSequenceLength);
		Geneious geneious = (Geneious) record.getMetadata().getAny();
		Sequencing sequencing = geneious.getDnaExtract().getDnaLabProject().getSequencing();
		sequencing.setConsensusSequenceID(csi);
		sequencing.setConsensusSequenceLength(csl);
	}

	private static void processConsensus(RecordType record, AnnotatedDocument ad)
	{
		String csi = ad.getDocument().getHiddenField(cache_name);
		String csl = ad.getDocument().getField(consensusSequenceLength);
		Geneious geneious = (Geneious) record.getMetadata().getAny();
		Sequencing sequencing = geneious.getDnaExtract().getDnaLabProject().getSequencing();
		sequencing.setConsensusSequenceID(csi);
		sequencing.setConsensusSequenceLength(csl);
	}

	private static void processOther(RecordType record, AnnotatedDocument ad)
	{
		Geneious geneious = (Geneious) record.getMetadata().getAny();
		Sequencing sequencing = geneious.getDnaExtract().getDnaLabProject().getSequencing();
		sequencing.setConsensusSequenceID(null);
		sequencing.setConsensusSequenceLength(null);
		sequencing.setConsensusSequenceQuality(null);
	}

}
