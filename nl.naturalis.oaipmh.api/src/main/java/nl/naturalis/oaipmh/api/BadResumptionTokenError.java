package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_RESUMPTION_TOKEN;

public class BadResumptionTokenError extends OAIPMHError {

	public BadResumptionTokenError()
	{
		super(BAD_RESUMPTION_TOKEN, "Error parsing resumption token");
	}

	public BadResumptionTokenError(String message)
	{
		super(BAD_RESUMPTION_TOKEN, message);
	}

}
