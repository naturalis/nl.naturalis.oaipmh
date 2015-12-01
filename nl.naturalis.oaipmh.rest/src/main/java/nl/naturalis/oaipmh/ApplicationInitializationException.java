package nl.naturalis.oaipmh;

/**
 * Exception thrown if anything goes wrong while configuring and initializing
 * REST service.
 * 
 * @see Registry
 * 
 * @author Ayco Holleman
 *
 */
@SuppressWarnings("serial")
public class ApplicationInitializationException extends RuntimeException {

	/**
	 * @param message
	 */
	public ApplicationInitializationException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public ApplicationInitializationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationInitializationException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
