package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.*;

public class IdDoesNotExistError extends OAIPMHError {

	public IdDoesNotExistError(String id)
	{
		super(ID_DOES_NOT_EXIST, "No such id: \"" + id + "\"");
	}

	public IdDoesNotExistError(OAIPMHRequest request)
	{
		this(request.getIdentifier());
	}

}
