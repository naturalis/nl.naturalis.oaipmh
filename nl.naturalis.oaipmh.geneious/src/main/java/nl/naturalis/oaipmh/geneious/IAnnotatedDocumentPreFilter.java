package nl.naturalis.oaipmh.geneious;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Filters records from the annotated_document table <i>before</i> they are
 * converted from raw {@link ResultSet} data to {@link AnnotatedDocument}
 * instances. The idea is to implement simple, fail-fast checks, before
 * expensive operations like XML parsing take place. Pre-filters are explicitly
 * allowed to maintain state; one instance is used for all records coming back
 * from the database.
 * 
 * @see IAnnotatedDocumentPostFilter
 * @see IAnnotatedDocumentSetFilter
 * @see ListRecordsHandler
 * 
 * @author Ayco Holleman
 *
 */
public interface IAnnotatedDocumentPreFilter {

	/**
	 * Whether or not to filter out the provided database record.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	boolean accept(ResultSet rs) throws SQLException;

}