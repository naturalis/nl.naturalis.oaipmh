package nl.naturalis.oaipmh;

public abstract class AbstractRequestHandler {

	protected RequestContext context;

	public AbstractRequestHandler(RequestContext context)
	{
		this.context = context;
	}

	public abstract void handleRequest() throws OAIPMHException;
}
