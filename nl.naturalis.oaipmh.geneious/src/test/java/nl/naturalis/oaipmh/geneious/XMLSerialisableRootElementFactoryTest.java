package nl.naturalis.oaipmh.geneious;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.domainobject.util.DOMUtil;
import org.domainobject.util.FileUtil;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

public class XMLSerialisableRootElementFactoryTest {

	@Test
	public void testBuild() throws SAXParseException
	{
		XMLSerialisableRootElementFactory factory = new XMLSerialisableRootElementFactory();
		InputStream is = getClass().getResourceAsStream("/plugin_document_xml_01.xml");
		String xml = FileUtil.getContents(is);
		Element root = DOMUtil.getDocumentElement(xml);
		XMLSerialisableRootElement xsre = factory.build(root);
		assertNotNull("01", xsre.getName());
		assertEquals("02", "foo", xsre.getName());
		assertEquals("03", Boolean.TRUE, xsre.isDummyCharSequence());
	}

}
