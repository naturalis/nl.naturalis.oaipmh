package nl.naturalis.oaipmh;

/**
 * Base class for all exceptions in this library except {@link OAIPMHException} and
 * {@link ApplicationInitializationException}. {@code ResourceException}s will result in
 * abnormal responses, most likely an HTTP 500 response.
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class ResourceException extends Exception {

	public ResourceException()
	{
	}

	public ResourceException(String message)
	{
		super(message);
	}

	public ResourceException(Throwable cause)
	{
		super(cause);
	}

	public ResourceException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
