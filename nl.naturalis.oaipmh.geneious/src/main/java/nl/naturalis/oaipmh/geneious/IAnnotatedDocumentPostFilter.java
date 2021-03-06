package nl.naturalis.oaipmh.geneious;

/**
 * Filters records from the annotated_document table <i>after</i> they have been
 * converted to {@link AnnotatedDocument} instances. Post-filters have access to
 * structured data extracted from the document_xml and plugin_document_xml
 * columns. Post-filters are explicitly allowed to maintain state; one instance
 * is used for all records coming back from the database.
 * 
 * @see IAnnotatedDocumentPreFilter
 * @see IAnnotatedDocumentSetFilter
 * @see ListRecordsHandler
 * 
 * @author Ayco Holleman
 *
 */
public interface IAnnotatedDocumentPostFilter {

	/**
	 * Whether or not to filter out the provided {@link AnnotatedDocument}.
	 * 
	 * @param ad
	 * @return
	 */
	boolean accept(AnnotatedDocument ad);

	int getNumAccepted();

	int getNumDiscarded();

}