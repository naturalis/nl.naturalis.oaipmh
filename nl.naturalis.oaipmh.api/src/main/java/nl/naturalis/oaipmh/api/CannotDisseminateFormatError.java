package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.*;

public class CannotDisseminateFormatError extends OAIPMHError {

	public CannotDisseminateFormatError(String metadataPrefix)
	{
		super(CANNOT_DISSEMINATE_FORMAT, "Cannot disseminate format: \"" + metadataPrefix + "\"");
	}

	public CannotDisseminateFormatError(OAIPMHRequest request)
	{
		this(request.getMetadataPrefix());
	}

}
