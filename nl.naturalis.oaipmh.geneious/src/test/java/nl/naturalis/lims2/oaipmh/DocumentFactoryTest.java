package nl.naturalis.lims2.oaipmh;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.domainobject.util.FileUtil;
import org.domainobject.util.debug.BeanPrinter;
import org.junit.Test;

public class DocumentFactoryTest {

	@Test
	public void testCreateDocument()
	{
		InputStream is = getClass().getResourceAsStream("/document_xml_01.xml");
		String xml = FileUtil.getContents(is);
		Document doc = DocumentFactory.createDocument(xml);
		String actual = BeanPrinter.toString(doc);
		is = getClass().getResourceAsStream("/document_xml_01_deserialized");
		String expected = FileUtil.getContents(is);
		assertEquals("01", expected, actual);
		assertNotNull("02", doc.getReferencedDocuments());
		assertEquals("03", 1, doc.getReferencedDocuments().size());
		assertEquals("04", 9, doc.getNotes().count());
		assertEquals("05", DocumentClass.DefaultNucleotideGraphSequence, doc.getDocumentClass());
	}

}
