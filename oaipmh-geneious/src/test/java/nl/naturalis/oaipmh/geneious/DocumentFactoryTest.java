package nl.naturalis.oaipmh.geneious;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import nl.naturalis.oaipmh.util.FileUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DocumentFactoryTest {

  @Test
  public void testCreateDocument() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("/document_xml_01.xml")) {
      String xml = FileUtil.getContents(is);
      Document doc = DocumentFactory.createDocument(xml);
      String expected = FileUtil.getContents(is);
      // TODO finish !
      String actual = expected;
      assertEquals("01", expected, actual);
      assertNotNull("02", doc.getReferencedDocuments());
      assertEquals("03", 1, doc.getReferencedDocuments().size());
      assertEquals("04", 10, doc.getNotes().count());
      assertEquals("05", DocumentClass.DefaultNucleotideGraphSequence, doc.getDocumentClass());
    }
  }

}
