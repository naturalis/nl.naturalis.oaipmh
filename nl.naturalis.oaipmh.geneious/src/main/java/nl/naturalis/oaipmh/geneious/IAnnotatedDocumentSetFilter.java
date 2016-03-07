package nl.naturalis.oaipmh.geneious;

import java.util.List;

/**
 * A set filter prunes the entire set of {@link AnnotatedDocument} instances
 * retrieved from the the database. The assumption is that the entire set is
 * needed to assess whether any single element of it must be filtered out of it.
 * Set filters are applied after {@link IAnnotatedDocumentPostFilter
 * post-filters}, which are again applied after
 * {@link IAnnotatedDocumentPreFilter pre-filters}.
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