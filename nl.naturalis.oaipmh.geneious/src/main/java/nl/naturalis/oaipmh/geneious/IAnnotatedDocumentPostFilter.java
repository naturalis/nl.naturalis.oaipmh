package nl.naturalis.oaipmh.geneious;

/**
 * Filters records from the annotated_document table <i>after</i> they have been
 * converted to {@link AnnotatedDocument} instances. Therefore post-filters have
 * access to all information in the document_xml and plugin_document_xml
 * columns. Post-filters are explicitly allowed to maintain state; each concrete
 * implementation is instantiated just once and then used for all records coming
 * back from the database (no new instance is created for every record).
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

}