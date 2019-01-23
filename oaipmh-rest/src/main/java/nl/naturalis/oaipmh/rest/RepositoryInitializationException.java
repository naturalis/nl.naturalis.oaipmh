package nl.naturalis.oaipmh.rest;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.RepositoryException;

/**
 * Exception thrown if anything goes wrong while configuring and initializing an
 * {@link IOAIRepository OAI repository}.
 * 
 * @author Ayco Holleman
 *
 */
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
