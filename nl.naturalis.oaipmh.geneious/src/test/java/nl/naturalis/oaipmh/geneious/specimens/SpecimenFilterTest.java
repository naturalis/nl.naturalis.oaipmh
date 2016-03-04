package nl.naturalis.oaipmh.geneious.specimens;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.MockResultSet;
import nl.naturalis.oaipmh.geneious.specimens.SpecimenFilter;

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
