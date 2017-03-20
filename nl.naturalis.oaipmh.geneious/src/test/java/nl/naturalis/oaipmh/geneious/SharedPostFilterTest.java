package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.CRSCode_CRS;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.PlatePositionCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;
import static org.junit.Assert.*;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DefaultAlignmentDocument;
import nl.naturalis.oaipmh.geneious.Document;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.SharedPostFilter;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

import org.junit.Before;
import org.junit.Test;

public class SharedPostFilterTest {

	private static final Note[] requiredNotes = new Note[] { CRSCode_CRS, ExtractIDCode_Samples,
			MarkerCode_Seq, DocumentVersionCode_Seq, RegistrationNumberCode_Samples,
			ExtractPlateNumberCode_Samples, PlatePositionCode_Samples };

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
		assertFalse("04", filter.accept(ad));
		for (Note n : requiredNotes) {
			notes.set(n, "foo");
		}
		assertFalse("05", filter.accept(ad));
		notes.set(Note.CRSCode_CRS, "true");
		assertTrue("06", filter.accept(ad));
	}

}
