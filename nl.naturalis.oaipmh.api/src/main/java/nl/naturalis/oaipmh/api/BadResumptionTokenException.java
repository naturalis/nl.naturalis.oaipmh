package nl.naturalis.oaipmh.api;

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
