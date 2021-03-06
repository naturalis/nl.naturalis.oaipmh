package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.ID_DOES_NOT_EXIST;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * Convenience class narrowing the JAXB {@code OAIPMHerrorType} class to one for
 * IdDoesNotExist errors.
 * 
 * @author Ayco Holleman
 *
 */
public class IdDoesNotExistError extends OAIPMHerrorType {

	public IdDoesNotExistError(String id)
	{
		super();
		this.code = ID_DOES_NOT_EXIST;
		this.value = "No such id: \"" + id + "\"";
	}

	public IdDoesNotExistError(OAIPMHRequest request)
	{
		this(request.getIdentifier());
	}

}
