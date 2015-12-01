package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_RESUMPTION_TOKEN;

import org.openarchives.oai._2.OAIPMHerrorType;

public class BadResumptionTokenError extends OAIPMHerrorType {

	public BadResumptionTokenError()
	{
		this("Error parsing resumption token");
	}

	public BadResumptionTokenError(String message)
	{
		super();
		this.code = BAD_RESUMPTION_TOKEN;
		this.value = message;
	}

}
