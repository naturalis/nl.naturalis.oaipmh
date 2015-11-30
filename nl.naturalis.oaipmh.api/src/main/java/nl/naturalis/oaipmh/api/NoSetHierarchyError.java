package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.NO_SET_HIERARCHY;

public class NoSetHierarchyError extends OAIPMHError {

	public NoSetHierarchyError(String message)
	{
		super(NO_SET_HIERARCHY, message);
	}

}
