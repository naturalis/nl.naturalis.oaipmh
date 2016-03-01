package nl.naturalis.lims2.oaipmh;

/**
 * Filters records from the annotated_document table <i>after</i> they have been
 * converted to {@link AnnotatedDocument} instances. Therefore post filters have
 * access to all information in the document_xml and plugin_document_xml
 * columns.
 * 
 * @author Ayco Holleman
 *
 */
public interface IAnnotatedDocumentPostFilter {

	boolean accept(AnnotatedDocument ad);

}