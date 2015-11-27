package nl.naturalis.oaipmh;

import nl.naturalis.oaipmh.api.OAIPMHException;

public abstract class AbstractRequestHandler {

	protected RequestContext context;

	public AbstractRequestHandler(RequestContext context)
	{
		this.context = context;
	}

	public abstract void handleRequest() throws OAIPMHException;
}
