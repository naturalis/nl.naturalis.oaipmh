package nl.naturalis.oaipmh.geneious;

import org.w3c.dom.Element;

public class DefaultAlignmentDocumentFactory {

	public DefaultAlignmentDocumentFactory()
	{
	}

	@SuppressWarnings("static-method")
	public DefaultAlignmentDocument build(Element root)
	{
		DefaultAlignmentDocument result = new DefaultAlignmentDocument();
		if (root.hasAttribute("is_contig")) {
			String s = root.getAttribute("is_contig");
			result.setContig(Boolean.valueOf(s));
		}
		return result;
	}
}
