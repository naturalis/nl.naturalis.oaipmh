package nl.naturalis.oaipmh.geneious;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

import org.junit.Test;

public class SharedSetFilterTest {

	@Test
	public void testFilter()
	{

		// Superseded by ad2 (discard!)
		AnnotatedDocument ad0 = new AnnotatedDocument();
		Document doc0 = new Document();
		ad0.setDocument(doc0);
		DocumentNotes notes0 = new DocumentNotes();
		doc0.setNotes(notes0);
		notes0.set(Note.ExtractIDCode_Samples, "ID0");
		notes0.set(Note.MarkerCode_Seq, "MC0");
		notes0.set(Note.DocumentVersionCode_Seq, "0");

		// Superseded by ad2 (discard!)
		AnnotatedDocument ad1 = new AnnotatedDocument();
		Document doc1 = new Document();
		ad1.setDocument(doc1);
		DocumentNotes notes1 = new DocumentNotes();
		doc1.setNotes(notes1);
		notes1.set(Note.ExtractIDCode_Samples, "ID0");
		notes1.set(Note.MarkerCode_Seq, "MC0");
		notes1.set(Note.DocumentVersionCode_Seq, "1");

		// Select! (1)
		AnnotatedDocument ad2 = new AnnotatedDocument();
		Document doc2 = new Document();
		ad2.setDocument(doc2);
		DocumentNotes notes2 = new DocumentNotes();
		doc2.setNotes(notes2);
		notes2.set(Note.ExtractIDCode_Samples, "ID0");
		notes2.set(Note.MarkerCode_Seq, "MC0");
		notes2.set(Note.DocumentVersionCode_Seq, "2");

		// Select! (2)
		AnnotatedDocument ad3 = new AnnotatedDocument();
		Document doc3 = new Document();
		ad3.setDocument(doc3);
		DocumentNotes notes3 = new DocumentNotes();
		doc3.setNotes(notes3);
		notes3.set(Note.ExtractIDCode_Samples, "ID1");
		notes3.set(Note.MarkerCode_Seq, "MC0");
		notes3.set(Note.DocumentVersionCode_Seq, "0");

		// Select! (3)
		AnnotatedDocument ad4 = new AnnotatedDocument();
		Document doc4 = new Document();
		ad4.setDocument(doc4);
		DocumentNotes notes4 = new DocumentNotes();
		doc4.setNotes(notes4);
		notes4.set(Note.ExtractIDCode_Samples, "ID2");
		notes4.set(Note.MarkerCode_Seq, "MC0");
		notes4.set(Note.DocumentVersionCode_Seq, "0");

		// Superseded by ad8 (discard!)
		AnnotatedDocument ad5 = new AnnotatedDocument();
		Document doc5 = new Document();
		ad5.setDocument(doc5);
		DocumentNotes notes5 = new DocumentNotes();
		doc5.setNotes(notes5);
		notes5.set(Note.ExtractIDCode_Samples, "ID2");
		notes5.set(Note.MarkerCode_Seq, "MC1");
		notes5.set(Note.DocumentVersionCode_Seq, "0");

		// Superseded by ad8 (discard!)
		AnnotatedDocument ad6 = new AnnotatedDocument();
		Document doc6 = new Document();
		ad6.setDocument(doc6);
		DocumentNotes notes6 = new DocumentNotes();
		doc6.setNotes(notes6);
		notes6.set(Note.ExtractIDCode_Samples, "ID2");
		notes6.set(Note.MarkerCode_Seq, "MC1");
		notes6.set(Note.DocumentVersionCode_Seq, "99");

		// Superseded by ad8 (discard!)
		AnnotatedDocument ad7 = new AnnotatedDocument();
		Document doc7 = new Document();
		ad7.setDocument(doc7);
		DocumentNotes notes7 = new DocumentNotes();
		doc7.setNotes(notes7);
		notes7.set(Note.ExtractIDCode_Samples, "ID2");
		notes7.set(Note.MarkerCode_Seq, "MC1");
		notes7.set(Note.DocumentVersionCode_Seq, "300");

		// Select! (4)
		AnnotatedDocument ad8 = new AnnotatedDocument();
		Document doc8 = new Document();
		ad8.setDocument(doc8);
		DocumentNotes notes8 = new DocumentNotes();
		doc8.setNotes(notes8);
		notes8.set(Note.ExtractIDCode_Samples, "ID2");
		notes8.set(Note.MarkerCode_Seq, "MC1");
		notes8.set(Note.DocumentVersionCode_Seq, "2000");

		// Superseded by ad10 (discard!)
		AnnotatedDocument ad9 = new AnnotatedDocument();
		Document doc9 = new Document();
		ad9.setDocument(doc9);
		DocumentNotes notes9 = new DocumentNotes();
		doc9.setNotes(notes9);
		notes9.set(Note.ExtractIDCode_Samples, "ID3");
		notes9.set(Note.MarkerCode_Seq, "MC0");
		notes9.set(Note.DocumentVersionCode_Seq, "19");

		// Select! (5)
		AnnotatedDocument ad10 = new AnnotatedDocument();
		Document doc10 = new Document();
		ad10.setDocument(doc10);
		DocumentNotes notes10 = new DocumentNotes();
		doc10.setNotes(notes10);
		notes10.set(Note.ExtractIDCode_Samples, "ID3");
		notes10.set(Note.MarkerCode_Seq, "MC0");
		notes10.set(Note.DocumentVersionCode_Seq, "20");

		List<AnnotatedDocument> input = Arrays.asList(ad0, ad1, ad2, ad3, ad4, ad5, ad6, ad7, ad8,
				ad9, ad10);

		SharedSetFilter ssf = new SharedSetFilter();
		ssf.setUseReferenceComparator(false);
		List<AnnotatedDocument> output = ssf.filter(input);
		assertEquals("01", 5, output.size());
		assertEquals("02", ad2, output.get(0));
		assertEquals("03", ad3, output.get(1));
		assertEquals("04", ad4, output.get(2));
		assertEquals("05", ad8, output.get(3));
		assertEquals("06", ad10, output.get(4));

		// Make sure filtering not affected by order of AnnotatedDocument
		// instances
		Collections.shuffle(input);

		ssf = new SharedSetFilter();
		ssf.setUseReferenceComparator(false);
		output = ssf.filter(input);
		assertEquals("07", 5, output.size());
		assertEquals("08", ad2, output.get(0));
		assertEquals("09", ad3, output.get(1));
		assertEquals("10", ad4, output.get(2));
		assertEquals("11", ad8, output.get(3));
		assertEquals("12", ad10, output.get(4));
	}

}
