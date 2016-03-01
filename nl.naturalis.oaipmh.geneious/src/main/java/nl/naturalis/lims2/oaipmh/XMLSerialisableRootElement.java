package nl.naturalis.lims2.oaipmh;

import java.util.List;

/**
 * Models the contents of the plugin_document_xml column in case the root
 * element is &lt;XMLSerialisableRootElement&gt;.
 * 
 * @author Ayco Holleman
 *
 */
public class XMLSerialisableRootElement extends PluginDocument {

	private String name;
	private String description;
	private Boolean dummyCharSequence;
	private Boolean finishedAddingOutputDocuments;
	private List<String> inputDocuments;
	private String outputDocument;
	private String operationId;

	public XMLSerialisableRootElement()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Boolean isDummyCharSequence()
	{
		return dummyCharSequence;
	}

	public void setDummyCharSequence(Boolean dummyCharSequence)
	{
		this.dummyCharSequence = dummyCharSequence;
	}

	public Boolean isFinishedAddingOutputDocuments()
	{
		return finishedAddingOutputDocuments;
	}

	public void setFinishedAddingOutputDocuments(Boolean finishedAddingOutputDocuments)
	{
		this.finishedAddingOutputDocuments = finishedAddingOutputDocuments;
	}

	public List<String> getInputDocuments()
	{
		return inputDocuments;
	}

	public void setInputDocuments(List<String> inputDocuments)
	{
		this.inputDocuments = inputDocuments;
	}

	public String getOutputDocument()
	{
		return outputDocument;
	}

	public void setOutputDocument(String outputDocument)
	{
		this.outputDocument = outputDocument;
	}

	public String getOperationId()
	{
		return operationId;
	}

	public void setOperationId(String operationId)
	{
		this.operationId = operationId;
	}

}
