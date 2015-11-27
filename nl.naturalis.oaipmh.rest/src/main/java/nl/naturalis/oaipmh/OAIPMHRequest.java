package nl.naturalis.oaipmh;

import java.util.Date;

import org.openarchives.oai._2.VerbType;

/**
 * Class modeling an OAI-PMH request.
 * 
 * @author Ayco Holleman
 *
 */
public class OAIPMHRequest {

	private VerbType verb;
	private String metadataPrefix;
	private String identifier;
	private Date from;
	private Date until;
	private String set;
	private String resumptionToken;
	private int page;

	public VerbType getVerb()
	{
		return verb;
	}

	public void setVerb(VerbType verb)
	{
		this.verb = verb;
	}

	public String getMetadataPrefix()
	{
		return metadataPrefix;
	}

	public void setMetadataPrefix(String metadataPrefix)
	{
		this.metadataPrefix = metadataPrefix;
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public Date getFrom()
	{
		return from;
	}

	public void setFrom(Date from)
	{
		this.from = from;
	}

	public Date getUntil()
	{
		return until;
	}

	public void setUntil(Date until)
	{
		this.until = until;
	}

	public String getSet()
	{
		return set;
	}

	public void setSet(String set)
	{
		this.set = set;
	}

	public String getResumptionToken()
	{
		return resumptionToken;
	}

	public void setResumptionToken(String resumptionToken)
	{
		this.resumptionToken = resumptionToken;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

}
