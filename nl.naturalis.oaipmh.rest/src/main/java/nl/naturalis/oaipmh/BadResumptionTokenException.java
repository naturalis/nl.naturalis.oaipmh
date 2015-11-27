package nl.naturalis.oaipmh;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_RESUMPTION_TOKEN;

@SuppressWarnings("serial")
class BadResumptionTokenException extends OAIPMHException {

	public BadResumptionTokenException()
	{
		super(BAD_RESUMPTION_TOKEN, "Error parsing resumption token");
	}

	public BadResumptionTokenException(String msg)
	{
		super(BAD_RESUMPTION_TOKEN, msg);
	}

}
