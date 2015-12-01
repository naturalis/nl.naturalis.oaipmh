package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_ARGUMENT;

import org.openarchives.oai._2.OAIPMHerrorType;

public class BadArgumentError extends OAIPMHerrorType {

	public BadArgumentError(String message)
	{
		super();
		this.code = BAD_ARGUMENT;
		this.value = message;
	}

}
