package nl.naturalis.lims2.oaipmh;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.DOMUtil;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

/**
 * A factory for {@link Document} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentFactory {

	private static final Logger logger = LogManager.getLogger(DocumentFactory.class);

	private static final String ERR_BAD_XML = "Error parsing {}; {}\n\n{}";

	/**
	 * Creates a new {@link Document} instance from the XML in the document_xml
	 * column of the annotated_document table.
	 * 
	 * @param xml
	 * @return
	 */
	public static Document createDocument(String xml)
	{
		if (logger.isTraceEnabled()) {
			logger.trace("Parsing contents of column \"document_xml\"");
		}
		Element root;
		try {
			root = DOMUtil.getDocumentElement(xml.trim());
		}
		catch (SAXParseException e) {
			logger.error(ERR_BAD_XML, "document_xml", e.getMessage(), xml);
			return null;
		}
		Document doc = new Document();
		DocumentClass documentClass = getDocumentClass(root);
		doc.setDocumentClass(documentClass);
		DocumentNotes notes = getDocumentNotes(root);
		doc.setNotes(notes);
		List<String> urns = getReferencedDocuments(root);
		doc.setReferencedDocuments(urns);
		return doc;
	}

	private static DocumentClass getDocumentClass(Element root)
	{
		String s = root.getAttribute("class");
		DocumentClass documentClass = DocumentClass.parse(s);
		return documentClass;
	}

	private static DocumentNotes getDocumentNotes(Element root)
	{
		Element notesElement = DOMUtil.getChild(root, "notes");
		if (notesElement == null) {
			return null;
		}
		DocumentNotes notes = null;
		for (DocumentNotes.Note note : DocumentNotes.Note.values()) {
			Element e = DOMUtil.getDescendant(notesElement, note.name());
			if (e != null) {
				if (logger.isTraceEnabled()) {
					logger.trace("Found document note for {}", note.name());
				}
				if (notes == null) {
					notes = new DocumentNotes();
				}
				notes.set(note, e.getTextContent());
			}
		}
		if (logger.isDebugEnabled()) {
			int i = notes == null ? 0 : notes.count();
			logger.debug("Number of usable <note> elements: {}", i);
		}
		return notes;
	}

	private static List<String> getReferencedDocuments(Element root)
	{
		List<String> urns = null;
		Element e = DOMUtil.getDescendant(root, "referenced_documents");
		if (e != null) {
			List<Element> elems = DOMUtil.getChildren(e);
			if (elems.size() > 0) {
				urns = new ArrayList<>(elems.size());
				for (Element elem : elems) {
					String val = elem.getTextContent();
					int i = val.indexOf('@');
					assert (i != -1);
					urns.add(val.substring(0, i));
				}
			}
		}
		return urns;
	}

}
