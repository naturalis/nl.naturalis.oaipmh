package nl.naturalis.oaipmh.api;

import static nl.naturalis.oaipmh.api.util.ObjectFactories.oaiFactory;

import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.OAIPMHerrorcodeType;

public class OAIPMHError {

	private OAIPMHerrorcodeType code;
	private String message;

	public OAIPMHError(OAIPMHerrorcodeType code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public OAIPMHerrorcodeType getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}

	public OAIPMHerrorType toXML()
	{
		OAIPMHerrorType error = oaiFactory.createOAIPMHerrorType();
		error.setCode(code);
		error.setValue(message);
		return error;
	}

}
