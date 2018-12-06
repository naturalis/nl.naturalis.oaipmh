package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.NO_SET_HIERARCHY;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * Convenience class narrowing the JAXB {@code OAIPMHerrorType} class to one for
 * NoSetHierarchy errors.
 * 
 * @author Ayco Holleman
 *
 */
public class NoSetHierarchyError extends OAIPMHerrorType {

	public NoSetHierarchyError(String message)
	{
		super();
		this.code = NO_SET_HIERARCHY;
		this.value = message;
	}

	public NoSetHierarchyError(OAIPMHRequest request)
	{
		super();
		this.code = NO_SET_HIERARCHY;
		this.value = "Sets not supported. (Set provided: " + request.getSet() + ")";
	}

	public NoSetHierarchyError()
	{
		super();
		this.code = NO_SET_HIERARCHY;
	}

}
