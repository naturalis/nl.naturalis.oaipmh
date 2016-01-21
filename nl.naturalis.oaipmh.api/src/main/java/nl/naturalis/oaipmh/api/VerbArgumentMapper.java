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

class VerbArgumentMapper {

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

	Set<Argument> getRequiredArguments(VerbType verb)
	{
		return required.get(verb);
	}

	Set<Argument> getOptionalArguments(VerbType verb)
	{
		return optional.get(verb);
	}

}
