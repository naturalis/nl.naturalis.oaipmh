package nl.naturalis.oaipmh.geneious;

import java.util.List;

import org.domainobject.util.CollectionUtil;
import org.domainobject.util.DOMUtil;
import org.domainobject.util.convert.Stringifier;
import org.w3c.dom.Element;

/**
 * Factory for {@link XMLSerialisableRootElement} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class XMLSerialisableRootElementFactory {

	private static final String DUMMY_CHAR_SEQUENCE = "NNNNNNNNNN";

	public XMLSerialisableRootElementFactory()
	{
	}

	/**
	 * Converts the XML contents of the plugin_document_xml column to an
	 * instance of {@link XMLSerialisableRootElement}.
	 * 
	 * @param root
	 * @return
	 */
	@SuppressWarnings("static-method")
	public XMLSerialisableRootElement build(Element root)
	{
		XMLSerialisableRootElement result = new XMLSerialisableRootElement();
		result.setName(DOMUtil.getValue(root, "name"));
		result.setDescription(DOMUtil.getValue(root, "description"));
		String charSequence = DOMUtil.getValue(root, "charSequence");
		if (charSequence != null) {
			result.setDummyCharSequence(charSequence.equals(DUMMY_CHAR_SEQUENCE));
		}
		result.setOutputDocument(DOMUtil.getValue(root, "outputDocument"));
		if (root.hasAttribute("finishedAddingOutputDocuments")) {
			String s = root.getAttribute("finishedAddingOutputDocuments");
			result.setFinishedAddingOutputDocuments(Boolean.valueOf(s));
		}
		List<String> inputDocuments = getInputDocuments(root);
		result.setInputDocuments(inputDocuments);
		return result;
	}

	private static List<String> getInputDocuments(Element root)
	{
		List<Element> elems = DOMUtil.getChildren(root, "inputDocument");
		if (elems != null) {
			return CollectionUtil.stringify(elems, new Stringifier<Element>() {
				@Override
				public String execute(Element obj, Object... conversionArguments)
				{
					return obj.getAttribute("weakReference");
				}
			});
		}
		return null;
	}

}
