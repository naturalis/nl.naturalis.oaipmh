package nl.naturalis.lims2.oaipmh.specimens;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.MockResultSet;

import org.junit.Test;

public class SpecimenFilterTest {

	@Test()
	@SuppressWarnings({ "static-method", "resource" })
	public void testAcceptResultSet() throws SQLException
	{
		SpecimenFilter filter = new SpecimenFilter();
		MockResultSet rs = new MockResultSet();
		assertTrue("01", filter.accept(rs));
	}

	@Test()
	@SuppressWarnings("static-method")
	public void testAcceptAnnotatedDocument()
	{
		SpecimenFilter filter = new SpecimenFilter();
		AnnotatedDocument ad = new AnnotatedDocument();
		assertTrue("01", filter.accept(ad));
	}
}
