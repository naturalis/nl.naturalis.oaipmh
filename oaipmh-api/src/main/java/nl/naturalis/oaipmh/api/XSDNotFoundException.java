package nl.naturalis.oaipmh.api;

/**
 * Can be thrown by
 * {@link IOAIRepository#getXSDForMetadataPrefix(java.io.OutputStream, String)}
 * if the requested XSD cannot be served.
 * 
 * @author Ayco Holleman
 *
 */
public class XSDNotFoundException extends RepositoryException {

	private static final long serialVersionUID = 2951898716503956245L;

	public XSDNotFoundException(String nsPrefix)
	{
		super("No XSD found for XML namespace prefix \"" + nsPrefix + "\"");
	}

}
