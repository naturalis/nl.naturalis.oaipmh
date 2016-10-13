package nl.naturalis.oaipmh.geneious;

import nl.naturalis.oaipmh.api.RepositoryException;

/**
 * Thrown by {@link IAnnotatedDocumentPostProcessor} instances in case an error
 * condition arises.
 * 
 * @author Ayco Holleman
 *
 */
public class PostProcessingException extends RepositoryException {

	public PostProcessingException()
	{
		super();
	}

	public PostProcessingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PostProcessingException(String message)
	{
		super(message);
	}

	public PostProcessingException(Throwable cause)
	{
		super(cause);
	}

}
