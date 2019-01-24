package nl.naturalis.oaipmh.geneious;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import nl.naturalis.oaipmh.util.DOMUtil;
import nl.naturalis.oaipmh.util.FileUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XMLSerialisableRootElementFactoryTest {

  @Test
  public void testBuild() throws SAXParseException, IOException {
    XMLSerialisableRootElementFactory factory = new XMLSerialisableRootElementFactory();
    try (InputStream is = getClass().getResourceAsStream("/plugin_document_xml_01.xml")) {
      String xml = FileUtil.getContents(is);
      Element root = DOMUtil.getDocumentElement(xml);
      XMLSerialisableRootElement xsre = factory.build(root);
      assertNotNull("01", xsre.getName());
      assertEquals("02", "foo", xsre.getName());
      assertEquals("03", Boolean.TRUE, xsre.isDummyCharSequence());
    }
  }

}
