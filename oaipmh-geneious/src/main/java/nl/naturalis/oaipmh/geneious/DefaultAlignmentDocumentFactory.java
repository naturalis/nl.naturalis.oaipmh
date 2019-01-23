package nl.naturalis.oaipmh.geneious;

import org.w3c.dom.Element;

/**
 * A factory for {@link DefaultAlignmentDocument} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class DefaultAlignmentDocumentFactory {

  public DefaultAlignmentDocumentFactory() {}

  /**
   * Creates a new {@link DefaultAlignmentDocument} instance.
   * 
   * @param root
   * @return
   */
  public DefaultAlignmentDocument build(Element root) {
    DefaultAlignmentDocument result = new DefaultAlignmentDocument();
    if (root.hasAttribute("is_contig")) {
      String s = root.getAttribute("is_contig");
      result.setContig(Boolean.valueOf(s));
    }
    return result;
  }
}
