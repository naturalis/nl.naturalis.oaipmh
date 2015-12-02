package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_VERB;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * Convenience class narrowing the JAXB {@code OAIPMHerrorType} class to one for
 * BadVerb errors.
 * 
 * @author Ayco Holleman
 *
 */
public class BadVerbError extends OAIPMHerrorType {

	public BadVerbError(String verbArg)
	{
		super();
		this.code = BAD_VERB;
		this.value = "Bad verb: \"" + verbArg + "\"";
	}

	public BadVerbError()
	{
		super();
		this.code = BAD_VERB;
		this.value = "Missing verb";
	}

}
