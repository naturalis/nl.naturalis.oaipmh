package nl.naturalis.oaipmh;

import java.util.Arrays;
import java.util.List;

import nl.naturalis.oaipmh.rest.OAIPMHResource;

import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.OAIPMHerrorcodeType;

/**
 * An {@code OAIPMHException} is thrown in response to an error condition explicitly
 * mentioned by the OAI-PMH specs. These error conditions do not result in abnormal
 * responses. The server still responds with HTTP 200 (OK), and the response body still
 * contains valid OAI-PMH XML.
 * 
 * @see http://www.openarchives.org/OAI/openarchivesprotocol.html#ErrorConditions
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class OAIPMHException extends Exception {

	public static OAIPMHerrorType createError(OAIPMHerrorcodeType code, String message)
	{
		OAIPMHerrorType error = OAIPMHResource.OAI_FACTORY.createOAIPMHerrorType();
		error.setCode(code);
		error.setValue(message);
		return error;
	}

	private List<OAIPMHerrorType> errors;

	public OAIPMHException(OAIPMHerrorcodeType code, String message)
	{
		this(createError(code, message));
	}

	public OAIPMHException(OAIPMHerrorType error)
	{
		errors = Arrays.asList(error);
	}

	public OAIPMHException(List<OAIPMHerrorType> errors)
	{
		this.errors = errors;
	}

	public List<OAIPMHerrorType> getErrors()
	{
		return errors;
	}
}
