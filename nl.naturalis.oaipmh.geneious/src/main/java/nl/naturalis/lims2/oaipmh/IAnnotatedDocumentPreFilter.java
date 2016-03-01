package nl.naturalis.lims2.oaipmh;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IAnnotatedDocumentPreFilter {

	boolean accept(ResultSet rs) throws SQLException;

}