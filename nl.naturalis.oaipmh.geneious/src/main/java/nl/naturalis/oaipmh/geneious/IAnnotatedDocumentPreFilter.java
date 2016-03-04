package nl.naturalis.oaipmh.geneious;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Filters records from the annotated_document table <i>before</i> they are
 * converted from raw database result set data to {@link AnnotatedDocument}
 * instances. The idea is to implement simple, fail-fast checks, before any XML
 * parsing has taken place. Pre-filters are explicitly allowed to maintain
 * state; each concrete implementation is instantiated just once and then used
 * for all records coming back from the database (no new instance is created for
 * every record).
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