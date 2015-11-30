package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_ARGUMENT;

import org.openarchives.oai._2.OAIPMHerrorcodeType;

public class BadArgumentError extends OAIPMHError {

	public BadArgumentError(String message)
	{
		super(BAD_ARGUMENT, message);
	}

}
