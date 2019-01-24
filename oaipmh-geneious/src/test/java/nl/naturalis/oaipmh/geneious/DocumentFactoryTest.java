package nl.naturalis.oaipmh.geneious;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import nl.naturalis.oaipmh.util.BeanPrinter;
import nl.naturalis.oaipmh.util.FileUtil;

public class DocumentFactoryTest {

  @Test
  public void testCreateDocument() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("/document_xml_01.xml")) {
      String xml = FileUtil.getContents(is);
      Document doc = DocumentFactory.createDocument(xml);
      String actual = BeanPrinter.toString(doc);
      System.out.println(actual);
    }
    // is = getClass().getResourceAsStream("/document_xml_01_deserialized");
    // String expected = FileUtil.getContents(is);
    // assertEquals("01", expected, actual);
    // assertNotNull("02", doc.getReferencedDocuments());
    // assertEquals("03", 1, doc.getReferencedDocuments().size());
    // assertEquals("04", 10, doc.getNotes().count());
    // assertEquals("05", DocumentClass.DefaultNucleotideGraphSequence,
    // doc.getDocumentClass());
  }

}
