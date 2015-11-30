package nl.naturalis.oaipmh.api;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@code OAIPMHException} is thrown in response to an error condition
 * explicitly mentioned by the OAI-PMH specs. These error conditions do not
 * result in abnormal HTTP responses. The server still responds with HTTP 200
 * (OK), and the response body still contains valid OAI-PMH XML. Error
 * conditions not explicitly mentioned in the specs should result in a
 * {@link RepositoryException}.
 * 
 * @see http
 *      ://www.openarchives.org/OAI/openarchivesprotocol.html#ErrorConditions
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class OAIPMHException extends Exception {

	private final List<OAIPMHError> errors;

	public OAIPMHException(OAIPMHError error)
	{
		errors = new ArrayList<OAIPMHError>(1);
		errors.add(error);
	}

	public OAIPMHException(List<OAIPMHError> errors)
	{
		this.errors = errors;
	}

	/**
	 * Returns the errors captured by this instance.
	 * 
	 * @return
	 */
	public List<OAIPMHError> getErrors()
	{
		return errors;
	}

	/**
	 * Returns the first error captured by this instance. Convenient if you
	 * <i>know</i> it is the only error captured by this instance.
	 * 
	 * @return
	 */
	public OAIPMHError getError()
	{
		return errors.get(0);
	}
}
