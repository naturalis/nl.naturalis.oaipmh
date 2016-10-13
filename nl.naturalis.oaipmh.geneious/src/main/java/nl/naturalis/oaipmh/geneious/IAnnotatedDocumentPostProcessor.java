package nl.naturalis.oaipmh.geneious;

/**
 * Allows for the manipulation of {@link AnnotatedDocument} instances once they
 * have gone through all filters.
 * 
 * @author Ayco Holleman
 *
 */
public interface IAnnotatedDocumentPostProcessor {

	void process(AnnotatedDocument annotatedDocument) throws PostProcessingException;

}
