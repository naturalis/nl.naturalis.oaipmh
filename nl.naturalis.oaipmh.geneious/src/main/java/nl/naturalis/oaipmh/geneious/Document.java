package nl.naturalis.oaipmh.geneious;

import java.util.List;

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
	private String description;
	private DocumentNotes notes;

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

	public String getDescription()
	{
		return description;
	}

	/**
	 * Returns the contents of the &lt;description&gt; element within the
	 * &lt;hiddenFields&gt; element.
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
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

}
