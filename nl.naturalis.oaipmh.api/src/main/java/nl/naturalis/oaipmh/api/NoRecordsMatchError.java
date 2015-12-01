package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.NO_RECORDS_MATCH;

import org.openarchives.oai._2.OAIPMHerrorType;

public class NoRecordsMatchError extends OAIPMHerrorType {

	public NoRecordsMatchError()
	{
		super();
		this.code = NO_RECORDS_MATCH;
		this.value = "No records match";
	}

}
