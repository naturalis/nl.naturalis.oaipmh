package nl.naturalis.oaipmh.geneious;

import java.util.List;

/**
 * A set filter filters out annotated_document records within the context of the
 * entire set of records retrieved from the database. The assumption is that the
 * entire set is need to assess whether any single element of it must be
 * filtered out of it.
 * 
 * @author Ayco Holleman
 *
 */
public interface IAnnotatedDocumentSetFilter {

	/**
	 * Prune the provided list of {@link AnnotatedDocument} instances.
	 * 
	 * @param input
	 * @return
	 */
	List<AnnotatedDocument> filter(List<AnnotatedDocument> input);

}