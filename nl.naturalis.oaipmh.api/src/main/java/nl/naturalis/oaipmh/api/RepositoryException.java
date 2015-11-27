package nl.naturalis.oaipmh.api;

/**
 * Base class for all exceptions in this library except {@link OAIPMHException}
 * and {@link ApplicationInitializationException}. {@code ResourceException}s
 * will result in abnormal responses, most likely an HTTP 500 response.
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class RepositoryException extends Exception {

	public RepositoryException()
	{
	}

	public RepositoryException(String message)
	{
		super(message);
	}

	public RepositoryException(Throwable cause)
	{
		super(cause);
	}

	public RepositoryException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
