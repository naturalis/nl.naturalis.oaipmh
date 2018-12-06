package nl.naturalis.oaipmh.geneious;

import java.util.List;

/**
 * A set filter takes an entire set of {@link AnnotatedDocument} instances in
 * order to get rid of discardable ones. The assumption is that the entire set
 * is needed to assess whether any single element of it must be filtered away.
 * Set filters should not rely on the input set being sorted in any particular
 * way, because any set filter in the set filter chain may shuffle the elements
 * for its own purposes.
 * 
 * @see IAnnotatedDocumentPreFilter
 * @see IAnnotatedDocumentPostFilter
 * @see ListRecordsHandler
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