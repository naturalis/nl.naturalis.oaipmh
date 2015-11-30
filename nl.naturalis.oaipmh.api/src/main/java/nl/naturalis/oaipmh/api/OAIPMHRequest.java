package nl.naturalis.oaipmh.api;

import java.net.URI;
import java.util.Date;

import nl.naturalis.oaipmh.api.util.OAIPMHUtil;

import org.openarchives.oai._2.VerbType;

/**
 * Class modeling an OAI-PMH request. Besides the OAI-PMH verb and the 6 OAI-PMH
 * arguments (metadataPrefix, identifier, from, until, set, resumptionToken),
 * this class provides the following extra information:
 * <ol>
 * <li>{@code page} - The requested page number when paging through large data
 * sets using resumption token.
 * <li>{@code requestUri} - A URI instance wrapping the original HTTP request
 * URL.
 * <li>{@code dateFormatFrom} - The date format used for the from argument. This
 * will be either {@link OAIPMHUtil#dateTimeFormat} or
 * {@link OAIPMHUtil#dateFormat}.
 * <li>{@code dateFormatUntil} - The date format used for the until argument.
 * This will be either {@link OAIPMHUtil#dateTimeFormat} or
 * {@link OAIPMHUtil#dateFormat}.
 * </ol>
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
	private URI requestUri;
	private String dateFormatFrom;
	private String dateFormatUntil;

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

	public URI getRequestUri()
	{
		return requestUri;
	}

	public void setRequestUri(URI requestUri)
	{
		this.requestUri = requestUri;
	}

	public String getDateFormatFrom()
	{
		return dateFormatFrom;
	}

	public void setDateFormatFrom(String dateFormatFrom)
	{
		this.dateFormatFrom = dateFormatFrom;
	}

	public String getDateFormatUntil()
	{
		return dateFormatUntil;
	}

	public void setDateFormatUntil(String dateFormatUntil)
	{
		this.dateFormatUntil = dateFormatUntil;
	}

}
