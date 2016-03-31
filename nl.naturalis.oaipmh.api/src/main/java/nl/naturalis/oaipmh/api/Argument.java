package nl.naturalis.oaipmh.api;

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
		if (param != null) {
			for (Argument arg : values()) {
				if (arg.param.equals(param)) {
					return arg;
				}
			}
		}
		return null;
	}

	private final String param;

	private Argument(String param)
	{
		this.param = param;
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
