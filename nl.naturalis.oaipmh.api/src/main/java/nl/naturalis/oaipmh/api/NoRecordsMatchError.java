package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.NO_RECORDS_MATCH;

public class NoRecordsMatchError extends OAIPMHError {

	public NoRecordsMatchError()
	{
		super(NO_RECORDS_MATCH, "No records match");
	}

}
