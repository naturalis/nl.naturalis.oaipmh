package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.NO_SET_HIERARCHY;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * Narrows the JAXB {@OAIPMHerrorType} class to one for
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

}
