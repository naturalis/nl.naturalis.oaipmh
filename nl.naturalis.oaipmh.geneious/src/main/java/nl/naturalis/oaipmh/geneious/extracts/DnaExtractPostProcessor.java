package nl.naturalis.oaipmh.geneious.extracts;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostProcessor;
import nl.naturalis.oaipmh.geneious.PostProcessingException;

import org.openarchives.oai._2.RecordType;

/**
 * A post processor for DNA extracts.
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
	}

}
