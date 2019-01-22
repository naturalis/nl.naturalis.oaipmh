package nl.naturalis.oaipmh.geneious;

/**
 * Models the contents of the plugin_document_xml column in case the root
 * element is &lt;DefaultAlignmentDocument&gt;.
 * 
 * @author Ayco Holleman
 *
 */
public class DefaultAlignmentDocument extends PluginDocument {

	private Boolean contig;

	public DefaultAlignmentDocument()
	{
	}

	public Boolean isContig()
	{
		return contig;
	}

	public void setContig(Boolean contig)
	{
		this.contig = contig;
	}

}
