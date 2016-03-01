package nl.naturalis.lims2.oaipmh.specimens;

import static org.junit.Assert.*;
import nl.naturalis.lims2.oaipmh.AnnotatedDocument;
import nl.naturalis.lims2.oaipmh.Document;
import nl.naturalis.lims2.oaipmh.DocumentNotes;
import nl.naturalis.lims2.oaipmh.DocumentNotes.Note;

import org.junit.Test;

public class SpecimenFilterTest {

	@Test()
	@SuppressWarnings("static-method")
	public void testAcceptAnnotatedDocument()
	{
		SpecimenFilter filter = new SpecimenFilter();
		AnnotatedDocument ad = new AnnotatedDocument();
		assertFalse("01", filter.accept(ad));
		Document doc = new Document();
		ad.setDocument(doc);
		assertFalse("02", filter.accept(ad));
		DocumentNotes notes = new DocumentNotes();
		doc.setNotes(notes);
		assertFalse("03", filter.accept(ad));
		notes.set(Note.CRSCode_CRS, "");
		assertFalse("04", filter.accept(ad));
		notes.set(Note.CRSCode_CRS, "false");
		assertFalse("05", filter.accept(ad));
		notes.set(Note.CRSCode_CRS, "true");
		assertTrue("06", filter.accept(ad));
	}
}
