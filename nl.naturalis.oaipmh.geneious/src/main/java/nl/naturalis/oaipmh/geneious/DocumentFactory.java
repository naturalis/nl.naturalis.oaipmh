package nl.naturalis.oaipmh.geneious;

import java.util.ArrayList;
import java.util.List;

import nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField;

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
		doc.setDocumentClass(getDocumentClass(root));
		doc.setHiddenFields(getDocumentHiddenFields(root));
		doc.setFields(getDocumentFields(root));
		doc.setNotes(getDocumentNotes(root));
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
				if (notes == null) {
					notes = new DocumentNotes();
				}
				notes.set(note, e.getTextContent());
			}
		}
		if (logger.isDebugEnabled() && notes.count() == 0) {
			logger.debug("No usuable <note> elements found in XML");
		}
		return notes;
	}

	private static DocumentFields getDocumentFields(Element root)
	{
		Element fieldsElement = DOMUtil.getChild(root, "fields");
		if (fieldsElement == null) {
			return null;
		}
		DocumentFields fields = null;
		for (DocumentFields.Field field : DocumentFields.Field.values()) {
			Element e = DOMUtil.getChild(fieldsElement, field.name());
			if (e != null) {
				if (fields == null) {
					fields = new DocumentFields();
				}
				fields.set(field, e.getTextContent());
			}
		}
		return fields;
	}

	private static DocumentHiddenFields getDocumentHiddenFields(Element root)
	{
		Element hfElement = DOMUtil.getChild(root, "hiddenFields");
		if (hfElement == null) {
			return null;
		}
		DocumentHiddenFields fields = null;
		for (HiddenField field : HiddenField.values()) {
			Element e = DOMUtil.getChild(hfElement, field.name());
			if (e != null) {
				if (fields == null) {
					fields = new DocumentHiddenFields();
				}
				fields.set(field, e.getTextContent());
			}
		}
		return fields;
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
