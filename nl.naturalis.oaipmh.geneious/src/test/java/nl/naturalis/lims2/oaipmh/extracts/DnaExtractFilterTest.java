package nl.naturalis.lims2.oaipmh.extracts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import nl.naturalis.lims2.oaipmh.MockResultSet;

import org.junit.Test;

public class DnaExtractFilterTest {

	@Test
	@SuppressWarnings("static-method")
	public void testAcceptResultSet() throws SQLException
	{
		MockResultSet rs = new MockResultSet();
		rs.setString("document_xml", "foo");
		DnaExtractFilter filter = new DnaExtractFilter();
		assertFalse("01", filter.accept(rs));
		rs.setString("document_xml", "<ExtractIDCode_Samples>");
		assertTrue("01", filter.accept(rs));
	}

	@Test
	public void testAcceptAnnotatedDocument()
	{
		// Nothing to test
	}

}
