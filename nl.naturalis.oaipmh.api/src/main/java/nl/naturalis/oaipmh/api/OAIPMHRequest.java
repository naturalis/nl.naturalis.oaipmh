package nl.naturalis.oaipmh.api;

import java.net.URI;
import java.util.Date;

import nl.naturalis.oaipmh.api.util.OAIPMHUtil;

import org.openarchives.oai._2.VerbType;

/**
 * Standard Java bean modeling an OAI-PMH request. Instances of this class are
 * the only objects crossing the border from the REST layer to the
 * {@link IOAIRepository OAI repository}. The REST layer creates them, the OAI
 * repository reads them. Besides the 6 OAI-PMH &#34;arguments&#34; (verb,
 * metadataPrefix, identifier, from, until, set, resumptionToken), this class
 * provides the following extra information:
 * <ol>
 * <li>{@code page} - The requested page within the result set. Used when paging
 * through large data sets using resumption tokens. Note that the
 * &lt;resumptionToken&gt; element within OAI-PMH XML has a {@code cursor}
 * attribute, which is the <i>absolute</i> offset of the first record of the
 * requested page. However, the REST layer does not know what the page size
 * (number of records) for any particular OAI repository is. Therefore, the
 * repository implementation has to do the math itself when setting the
 * {@code cursor} attribute for the &lt;resumptionToken&gt; element (
 * {@code cursor = page * pageSize}).
 * <li>{@code requestUri} - The original HTTP request URL.
 * <li>{@code dateFormatFrom} - The date format used by the client for the from
 * argument. This will be either {@link OAIPMHUtil#dateTimeFormat} or
 * {@link OAIPMHUtil#dateFormat}.
 * <li>{@code dateFormatUntil} - The date format used by the client for the
 * until argument. This will be either {@link OAIPMHUtil#dateTimeFormat} or
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
