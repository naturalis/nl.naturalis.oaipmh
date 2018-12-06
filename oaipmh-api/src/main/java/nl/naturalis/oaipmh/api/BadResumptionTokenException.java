package nl.naturalis.oaipmh.api;

/**
 * Thrown when a resumption token cannot be decomposed into its constituent
 * parts. Basically an {@link OAIPMHException} instantiated with a
 * {@link BadResumptionTokenError}.
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class BadResumptionTokenException extends OAIPMHException {

	public BadResumptionTokenException()
	{
		super(new BadResumptionTokenError());
	}

	public BadResumptionTokenException(String message)
	{
		super(new BadResumptionTokenError(message));
	}

}
