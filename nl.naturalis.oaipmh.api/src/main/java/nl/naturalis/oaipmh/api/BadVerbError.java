package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_VERB;

public class BadVerbError extends OAIPMHError {

	public BadVerbError(String verbArg)
	{
		super(BAD_VERB, "Bad verb: \"" + verbArg + "\"");
	}

	public BadVerbError()
	{
		super(BAD_VERB, "Missing verb");
	}

}
