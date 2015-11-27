package nl.naturalis.oaipmh;

@SuppressWarnings("serial")
public class RepositoryInitializationException extends ResourceException {

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
