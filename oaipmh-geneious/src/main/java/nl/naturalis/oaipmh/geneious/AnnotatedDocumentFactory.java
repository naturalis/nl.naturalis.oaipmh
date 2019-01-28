package nl.naturalis.oaipmh.geneious;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import nl.naturalis.oaipmh.util.BeanPrinter;
import nl.naturalis.oaipmh.util.DOMUtil;

/**
 * A factory for {@link AnnotatedDocument} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class AnnotatedDocumentFactory {

  private static final Logger logger = LoggerFactory.getLogger(AnnotatedDocumentFactory.class);

  private static final String ERR_BAD_XML = "Error parsing {}; {}\n\n{}";

  AnnotatedDocumentFactory() {}

  /**
   * Creates a new {@link AnnotatedDocument} instance from the current record in the specified {@link ResultSet}.
   * 
   * @param rs
   * @return
   * @throws SQLException
   */
  public AnnotatedDocument build(ResultSet rs) throws SQLException {
    AnnotatedDocument ad = new AnnotatedDocument();
    ad.setId(rs.getInt("id"));
    ad.setUrn(rs.getString("urn"));
    ad.setFolderId(rs.getInt("folder_id"));
    ad.setModified(rs.getLong("modified"));
    ad.setReferenceCount(rs.getInt("reference_count"));
    String xml = rs.getString("document_xml");
    Document doc = DocumentFactory.createDocument(xml);
    ad.setDocument(doc);
    xml = rs.getString("plugin_document_xml");
    ad.setPluginDocument(parsePluginDocumentXML(xml));
    return ad;
  }

  private static PluginDocument parsePluginDocumentXML(String xml) {
    if (logger.isTraceEnabled()) {
      logger.trace("Parsing contents of column \"plugin_document_xml\"");
    }
    Element root;
    try {
      root = DOMUtil.getDocumentElement(xml.trim());
    } catch (SAXParseException e) {
      logger.error(ERR_BAD_XML, "plugin_document_xml", e.getMessage(), xml);
      return null;
    }
    if (root.getTagName().equals("XMLSerialisableRootElement")) {
      return new XMLSerialisableRootElementFactory().build(root);
    }
    if (root.getTagName().equals("DefaultAlignmentDocument")) {
      return new DefaultAlignmentDocumentFactory().build(root);
    }
    if (root.getTagName().equals("ABIDocument")) {
      return new ABIDocumentFactory().build(root);
    }
    return null;
  }

}
