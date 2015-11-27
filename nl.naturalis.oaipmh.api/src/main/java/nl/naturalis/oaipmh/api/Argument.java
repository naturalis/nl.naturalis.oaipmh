package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.VerbType.GET_RECORD;
import static org.openarchives.oai._2.VerbType.IDENTIFY;
import static org.openarchives.oai._2.VerbType.LIST_IDENTIFIERS;
import static org.openarchives.oai._2.VerbType.LIST_METADATA_FORMATS;
import static org.openarchives.oai._2.VerbType.LIST_RECORDS;
import static org.openarchives.oai._2.VerbType.LIST_SETS;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

import org.openarchives.oai._2.VerbType;

/**
 * An enumeration of the 6 OAI-PMH arguments. Note that, of these 6 arguments, the verb
 * argument actually is rather more like the name of the function being called than a
 * function argument.
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

	private static EnumMap<VerbType, EnumSet<Argument>> required;
	private static EnumMap<VerbType, EnumSet<Argument>> optional;

	static {
		EnumSet<Argument> none = EnumSet.noneOf(Argument.class);
		required = new EnumMap<>(VerbType.class);
		optional = new EnumMap<>(VerbType.class);
		required.put(IDENTIFY, none);
		required.put(LIST_METADATA_FORMATS, none);
		required.put(LIST_SETS, none);
		required.put(GET_RECORD, EnumSet.of(IDENTIFIER, METADATA_PREFIX));
		required.put(LIST_IDENTIFIERS, EnumSet.of(METADATA_PREFIX));
		required.put(LIST_RECORDS, EnumSet.of(METADATA_PREFIX));
		optional.put(IDENTIFY, none);
		optional.put(LIST_METADATA_FORMATS, EnumSet.of(IDENTIFIER));
		optional.put(LIST_SETS, EnumSet.of(RESUMPTION_TOKEN));
		optional.put(GET_RECORD, none);
		optional.put(LIST_IDENTIFIERS, EnumSet.of(FROM, UNTIL, SET));
		optional.put(LIST_RECORDS, EnumSet.of(FROM, UNTIL, SET, RESUMPTION_TOKEN));
	}

	/**
	 * Returns the enum constant corresponding to the specified HTTP request parameter.
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
		return required.get(verb);
	}

	private final String param;

	private Argument(String param)
	{
		this.param = param;
	}

	/**
	 * Whether or not this argument is an optional argument for the specified verb.
	 * 
	 * @param verb
	 * @return
	 */
	public boolean isOptional(VerbType verb)
	{
		return optional.get(verb).contains(this);
	}

	/**
	 * Returns the name of the HTTP request parameter used to specify the argument.
	 * 
	 * @return
	 */
	public String param()
	{
		return param;
	}

	/**
	 * Returns the name of the HTTP request parameter used to specify the argument.
	 * 
	 * @return
	 */
	@Override
	public String toString()
	{
		return param;
	}

}
