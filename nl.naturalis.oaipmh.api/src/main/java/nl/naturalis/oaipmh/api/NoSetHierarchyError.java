package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.NO_SET_HIERARCHY;

import org.openarchives.oai._2.OAIPMHerrorType;

public class NoSetHierarchyError extends OAIPMHerrorType {

	public NoSetHierarchyError(String message)
	{
		super();
		this.code = NO_SET_HIERARCHY;
		this.value = message;
	}

}
