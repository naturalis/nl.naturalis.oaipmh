package nl.naturalis.oaipmh.api.util;

import java.util.HashMap;

/**
 * Decorates the final OAIPMH output with namespace prefix declarations.
 * 
 * A more elegant way would have been to use a NamespacePrefixMapper
 * (com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper), but I got
 */

class NsPrefixMapper {

	private HashMap<String, String> namespacePrefixMap = new HashMap<>();

	NsPrefixMapper()
	{
	}

	void map(String namespace, String prefix)
	{
		namespacePrefixMap.put(namespace, prefix);
	}

}
