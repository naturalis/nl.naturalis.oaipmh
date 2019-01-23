package nl.naturalis.oaipmh.geneious;

/**
 * Filters records from the annotated_document table <i>after</i> they have been converted to {@link AnnotatedDocument} instances.
 * Post-filters have access to structured data extracted from the document_xml and plugin_document_xml columns. Post-filters are
 * allowed to maintain state: one instance is used for all records coming back from the database.
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

  /**
   * The number of record passing the filter.
   * 
   * @return
   */
  int getNumAccepted();

  /**
   * The number of records rejected by the filter.
   * 
   * @return
   */
  int getNumDiscarded();

}
