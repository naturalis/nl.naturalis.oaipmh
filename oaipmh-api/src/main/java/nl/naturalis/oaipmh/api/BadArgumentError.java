package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_ARGUMENT;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * Convenience class narrowing the JAXB {@code OAIPMHerrorType} class to one for
 * BadArgument errors.
 * 
 * @author Ayco Holleman
 *
 */
public class BadArgumentError extends OAIPMHerrorType {

	public BadArgumentError(String message)
	{
		super();
		this.code = BAD_ARGUMENT;
		this.value = message;
	}

}
