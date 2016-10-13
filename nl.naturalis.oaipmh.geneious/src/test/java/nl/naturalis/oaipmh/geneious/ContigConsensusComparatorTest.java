package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.description;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

@SuppressWarnings("static-method")
public class ContigConsensusComparatorTest {

	@Test
	public void testCompare_01()
	{
		AnnotatedDocument contig = new AnnotatedDocument();
		contig.setDocument(new Document());
		DefaultAlignmentDocument dad = new DefaultAlignmentDocument();
		dad.setContig(true);
		contig.setPluginDocument(dad);

		AnnotatedDocument consensus = new AnnotatedDocument();
		consensus.setDocument(new Document());
		XMLSerialisableRootElement xsre = new XMLSerialisableRootElement();
		xsre.setName("Foo consensus sequence");
		consensus.setPluginDocument(xsre);

		int i = new ContigConsensusComparator().compare(contig, consensus);

		assertEquals("01", 0, i);
		assertFalse("02", contig.doNotOutput);
		assertFalse("03", consensus.doNotOutput);
	}

	@Test
	public void testCompare_02()
	{

		AnnotatedDocument contig = new AnnotatedDocument();
		Document doc = new Document();
		doc.setHiddenField(description, "Assembly of something");
		contig.setDocument(doc);
		DefaultAlignmentDocument dad = new DefaultAlignmentDocument();
		dad.setContig(true);
		contig.setPluginDocument(dad);

		AnnotatedDocument consensus = new AnnotatedDocument();
		doc = new Document();
		doc.setHiddenField(description, "Assembly of something");
		consensus.setDocument(doc);
		XMLSerialisableRootElement xsre = new XMLSerialisableRootElement();
		xsre.setName("Foo consensus sequence");
		consensus.setPluginDocument(xsre);

		int i = new ContigConsensusComparator().compare(contig, consensus);

		assertEquals("01", 0, i);
		assertTrue("02", contig.doNotOutput);
		assertFalse("03", consensus.doNotOutput);
	}
}
