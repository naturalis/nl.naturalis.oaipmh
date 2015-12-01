package nl.naturalis.oaipmh.api;

import java.util.ArrayList;
import java.util.List;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * An {@code OAIPMHException} is thrown in response to an error condition
 * explicitly mentioned by the OAI-PMH specs. These error conditions do not
 * result in abnormal HTTP responses. The server still responds with HTTP 200
 * (OK), and the response body still contains valid OAI-PMH XML (albeit with
 * &lt;error&gt; elements). Error conditions not explicitly mentioned in the
 * specs must result in a {@link RepositoryException}. An
 * {@code OAIPMHException} can be thrown for more than one error, i.e. you can
 * save up all errors encountered while processing the request, and then throw
 * one {@code OAIPMHException} containing all of these errors. This is conform
 * the specifications, which explicitly discourage fail-fast implementations.
 * 
 * @see http
 *      ://www.openarchives.org/OAI/openarchivesprotocol.html#ErrorConditions
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class OAIPMHException extends Exception {

	private final List<OAIPMHerrorType> errors;

	public OAIPMHException(OAIPMHerrorType error)
	{
		errors = new ArrayList<OAIPMHerrorType>(1);
		errors.add(error);
	}

	public OAIPMHException(List<OAIPMHerrorType> errors)
	{
		this.errors = errors;
	}

	/**
	 * Returns the errors captured by this instance.
	 * 
	 * @return
	 */
	public List<OAIPMHerrorType> getErrors()
	{
		return errors;
	}

	/**
	 * Returns the first error captured by this instance. Convenient if you
	 * <i>know</i> it is the only error captured by this instance.
	 * 
	 * @return
	 */
	public OAIPMHerrorType getError()
	{
		return errors.get(0);
	}
}
