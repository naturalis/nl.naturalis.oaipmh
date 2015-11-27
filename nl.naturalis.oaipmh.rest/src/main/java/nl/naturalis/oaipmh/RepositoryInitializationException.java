package nl.naturalis.oaipmh;

import nl.naturalis.oaipmh.api.RepositoryException;

@SuppressWarnings("serial")
public class RepositoryInitializationException extends RepositoryException {

	public RepositoryInitializationException(String message)
	{
		super(message);
	}

	public RepositoryInitializationException(Throwable cause)
	{
		super(cause);
	}

	public RepositoryInitializationException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
