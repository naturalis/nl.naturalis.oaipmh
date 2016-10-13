package nl.naturalis.oaipmh.geneious;

import java.util.List;

import nl.naturalis.oaipmh.geneious.DocumentFields.Field;
import nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

/**
 * Standard Java Bean reflecting the contents of the document_xml column within
 * the annotated_document table.
 * 
 * @author Ayco Holleman
 *
 */
public class Document {

	private DocumentClass documentClass;
	private List<String> referencedDocuments;
	private DocumentHiddenFields hiddenFields;
	private DocumentFields fields;
	private DocumentNotes notes;

	public String getHiddenField(HiddenField hiddenField)
	{
		if (hiddenFields == null) {
			return null;
		}
		return hiddenFields.get(hiddenField);
	}

	public String getField(Field field)
	{
		if (fields == null) {
			return null;
		}
		return fields.get(field);
	}

	public String getNote(Note note)
	{
		if (notes == null) {
			return null;
		}
		return notes.get(note);
	}

	/**
	 * Returns the value of the "class" attribute of the root element
	 * 
	 * @return
	 */
	public DocumentClass getDocumentClass()
	{
		return documentClass;
	}

	/**
	 * Set the value of the "class" attribute of the root element
	 * 
	 * @return
	 */
	public void setDocumentClass(DocumentClass documentClass)
	{
		this.documentClass = documentClass;
	}

	/**
	 * Returns the records referenced by this record (that is, the record with
	 * the document_xml column from which this {link Document} instance was
	 * created).
	 * 
	 * @return
	 */
	public List<String> getReferencedDocuments()
	{
		return referencedDocuments;
	}

	/**
	 * Sets the records referenced by this record (that is, the record with the
	 * document_xml column from which this {link Document} instance was
	 * created).
	 * 
	 * @return
	 */
	public void setReferencedDocuments(List<String> referencedDocuments)
	{
		this.referencedDocuments = referencedDocuments;
	}

	/**
	 * Returns all relevant notes present in the document_xml column.
	 * 
	 * @return
	 */
	public DocumentNotes getNotes()
	{
		return notes;
	}

	/**
	 * Sets all relevant notes present in the document_xml column.
	 * 
	 * @return
	 */
	public void setNotes(DocumentNotes notes)
	{
		this.notes = notes;
	}

	/**
	 * Gets values from elements within the &lt;hiddenFields&gt; element in the
	 * document_xml column.
	 * 
	 * @return
	 */
	public DocumentHiddenFields getHiddenFields()
	{
		return hiddenFields;
	}

	/**
	 * Sets values from elements within the &lt;hiddenFields&gt; element in the
	 * document_xml column.
	 * 
	 * @param hiddenFields
	 */
	public void setHiddenFields(DocumentHiddenFields hiddenFields)
	{
		this.hiddenFields = hiddenFields;
	}

	/**
	 * Gets values from elements within the &lt;fields&gt; element in the
	 * document_xml column.
	 * 
	 * @return
	 */
	public DocumentFields getFields()
	{
		return fields;
	}

	/**
	 * Sets values from elements within the &lt;fields&gt; element in the
	 * document_xml column.
	 * 
	 * @param fields
	 */
	public void setFields(DocumentFields fields)
	{
		this.fields = fields;
	}

}
