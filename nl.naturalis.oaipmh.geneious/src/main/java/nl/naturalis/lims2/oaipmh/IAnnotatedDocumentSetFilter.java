package nl.naturalis.lims2.oaipmh;

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

	List<AnnotatedDocument> filter(List<AnnotatedDocument> input);

}