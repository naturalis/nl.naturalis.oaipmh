package nl.naturalis.lims2.oaipmh;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SharedPostFilterTest {

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	@SuppressWarnings("static-method")
	public void testAccept()
	{
		SharedPostFilter filter = new SharedPostFilter();
		AnnotatedDocument ad = new AnnotatedDocument();
		assertFalse("01", filter.accept(ad));
		Document document = new Document();
		ad.setDocument(document);
		DocumentNotes notes = new DocumentNotes();
		document.setNotes(notes);
		DefaultAlignmentDocument dad = new DefaultAlignmentDocument();
		ad.setPluginDocument(dad);
		assertFalse("02", filter.accept(ad));
		dad.setContig(Boolean.FALSE);
		assertFalse("03", filter.accept(ad));
		dad.setContig(Boolean.TRUE);
		assertTrue("04", filter.accept(ad));
	}

}
