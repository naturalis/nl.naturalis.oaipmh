package nl.naturalis.oaipmh;

import java.util.ArrayList;
import java.util.List;

import nl.naturalis.oaipmh.api.OAIPMHRequest;

import org.openarchives.oai._2.OAIPMHerrorType;

public class RequestContext {

	private OAIPMHRequest query;
	private List<OAIPMHerrorType> errors;

	public RequestContext()
	{
		this.errors = new ArrayList<OAIPMHerrorType>();
	}

	public OAIPMHRequest getQuery()
	{
		return query;
	}

	public void setQuery(OAIPMHRequest query)
	{
		this.query = query;
	}

	public List<OAIPMHerrorType> getErrors()
	{
		return errors;
	}

	public void addError(OAIPMHerrorType error)
	{
		this.errors.add(error);
	}

}
