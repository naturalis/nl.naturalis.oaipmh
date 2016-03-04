package nl.naturalis.oaipmh.api;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.IDENTIFIER;
import static nl.naturalis.oaipmh.api.Argument.METADATA_PREFIX;
import static nl.naturalis.oaipmh.api.Argument.RESUMPTION_TOKEN;
import static nl.naturalis.oaipmh.api.Argument.SET;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;
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
 * Maps each of the five OAI-PMH verbs to a set of required {@link Argument
 * arguments} and a set of optional arguments.
 * 
 * @author Ayco Holleman
 *
 */
class VerbArgumentMapper {

	private static final EnumSet<Argument> NONE = EnumSet.noneOf(Argument.class);

	private static VerbArgumentMapper instance;

	static VerbArgumentMapper getInstance()
	{
		if (instance == null)
			instance = new VerbArgumentMapper();
		return instance;
	}

	private final EnumMap<VerbType, EnumSet<Argument>> required;
	private final EnumMap<VerbType, EnumSet<Argument>> optional;

	VerbArgumentMapper()
	{
		required = new EnumMap<>(VerbType.class);
		optional = new EnumMap<>(VerbType.class);

		required.put(IDENTIFY, NONE);
		optional.put(IDENTIFY, NONE);

		required.put(LIST_METADATA_FORMATS, NONE);
		optional.put(LIST_METADATA_FORMATS, EnumSet.of(IDENTIFIER));

		required.put(LIST_SETS, NONE);
		optional.put(LIST_SETS, EnumSet.of(RESUMPTION_TOKEN));

		required.put(GET_RECORD, EnumSet.of(IDENTIFIER, METADATA_PREFIX));
		optional.put(GET_RECORD, NONE);

		required.put(LIST_IDENTIFIERS, EnumSet.of(METADATA_PREFIX));
		optional.put(LIST_IDENTIFIERS, EnumSet.of(FROM, UNTIL, SET));

		required.put(LIST_RECORDS, NONE);
		optional.put(LIST_RECORDS, EnumSet.of(FROM, UNTIL, SET, RESUMPTION_TOKEN, METADATA_PREFIX));
	}

	/**
	 * Returns the required arguments for the specified verb,
	 * 
	 * @param verb
	 * @return
	 */
	Set<Argument> getRequiredArguments(VerbType verb)
	{
		return required.get(verb);
	}

	/**
	 * Returns the optional (non-required) arguments for the specified verb.
	 * 
	 * @param verb
	 * @return
	 */
	Set<Argument> getOptionalArguments(VerbType verb)
	{
		return optional.get(verb);
	}

}
