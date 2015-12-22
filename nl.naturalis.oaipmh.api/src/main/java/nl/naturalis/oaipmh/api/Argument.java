package nl.naturalis.oaipmh.api;

import java.util.Set;

import org.openarchives.oai._2.VerbType;

/**
 * An enumeration of the seven URL query parameters allowed in a OAI-PMH
 * request. These are called &#34;arguments&#34; in the OAI-PMH specifications.
 * Note that, of these 7 arguments, the verb argument actually is more like the
 * name of the function being called than a function argument.
 * 
 * @author Ayco Holleman
 *
 */
public enum Argument {

	VERB("verb"),
	METADATA_PREFIX("metadataPrefix"),
	IDENTIFIER("identifier"),
	RESUMPTION_TOKEN("resumptionToken"),
	FROM("from"),
	UNTIL("until"),
	SET("set");

	/**
	 * Returns the enum constant corresponding to the specified HTTP request
	 * parameter.
	 * 
	 * @param param
	 * @return
	 */
	public static Argument parse(String param)
	{
		for (Argument arg : values()) {
			if (arg.param.equals(param))
				return arg;
		}
		return null;
	}

	/**
	 * Returns all required arguments for the specified verb.
	 * 
	 * @param verb
	 * @return
	 */
	public static Set<Argument> getRequiredArguments(VerbType verb)
	{
		return new VerbArgumentMapper().getRequiredArguments(verb);
	}

	private final String param;

	private Argument(String param)
	{
		this.param = param;
	}

	/**
	 * Whether or not this argument is a required argument for the specified
	 * verb.
	 * 
	 * @param verb
	 * @return
	 */
	public boolean isRequired(VerbType verb)
	{
		return new VerbArgumentMapper().getRequiredArguments(verb).contains(this);
	}

	/**
	 * Whether or not this argument is an optional argument for the specified
	 * verb.
	 * 
	 * @param verb
	 * @return
	 */
	public boolean isOptional(VerbType verb)
	{
		return new VerbArgumentMapper().getOptionalArguments(verb).contains(this);
	}

	/**
	 * Establishes whether this argument is an allowed argument for the
	 * specified verb. In other words whether this argument is either a required
	 * argument or an optional argument for the specified verb.
	 * 
	 * @param verb
	 * @return
	 */
	public boolean isAllowed(VerbType verb)
	{
		return isRequired(verb) || isOptional(verb);
	}

	/**
	 * Returns the name of the HTTP request parameter used to specify the
	 * argument.
	 * 
	 * @return
	 */
	public String param()
	{
		return param;
	}

	/**
	 * Returns the name of the HTTP request parameter used to specify the
	 * argument.
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		return param;
	}

}
