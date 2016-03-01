package nl.naturalis.lims2.oaipmh;

import org.w3c.dom.Element;

/**
 * Factory for {@link ABIDocument} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class ABIDocumentFactory {

	public ABIDocumentFactory()
	{
	}

	/**
	 * Creates a new {@link ABIDocument} instance from the specified XML root
	 * element, which has been created from the plugin_document_xml column in
	 * the annotated_document table.
	 * 
	 * @param root
	 * @return
	 */
	@SuppressWarnings("static-method")
	public ABIDocument build(Element root)
	{
		return new ABIDocument();
	}

}
