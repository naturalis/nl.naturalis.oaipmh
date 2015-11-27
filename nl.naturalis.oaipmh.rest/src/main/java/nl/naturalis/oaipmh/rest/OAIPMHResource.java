package nl.naturalis.oaipmh.rest;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;

import java.io.InputStream;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.naturalis.oaipmh.RepositoryFactory;
import nl.naturalis.oaipmh.RequestBuilder;
import nl.naturalis.oaipmh.RequestContext;
import nl.naturalis.oaipmh.api.Argument;
import nl.naturalis.oaipmh.api.IRepository;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;

import org.domainobject.util.StringUtil;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class OAIPMHResource {

	@SuppressWarnings("unused")
	private static final Logger logger;

	// JAXB object factories
	public static final org.openarchives.oai._2.ObjectFactory OAI_FACTORY;
	public static final org.openarchives.oai._2_0.oai_dc.ObjectFactory OAI_DC_FACTORY;
	public static final org.purl.dc.elements._1.ObjectFactory DC_FACTORY;

	static {
		logger = LoggerFactory.getLogger(OAIPMHResource.class);
		OAI_FACTORY = new org.openarchives.oai._2.ObjectFactory();
		OAI_DC_FACTORY = new org.openarchives.oai._2_0.oai_dc.ObjectFactory();
		DC_FACTORY = new org.purl.dc.elements._1.ObjectFactory();
	}

	@Context
	private HttpServletRequest request;
	@Context
	private UriInfo uriInfo;

	private OAIPMHRequest oaiRequest;
	private OAIPMHtype responseBody;

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{repo}")
	public Response handleRequest(@PathParam("repo") String repository)
	{
		RequestBuilder rb = RequestBuilder.newInstance();
		oaiRequest = rb.build(uriInfo);
		if (rb.getErrors().size() != 0) {
			responseBody.getError().addAll(rb.getErrors());
			return RestUtil.jaxbResponse(responseBody);
		}
		responseBody = createResponseSkeleton();
		try {
			IRepository repo = RepositoryFactory.getInstance().create(repository);
		}
		catch (RepositoryException e) {

		}
		catch (Throwable t) {

		}
		return null;
	}

	/**
	 * Show some welcome content.
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String welcome()
	{
		InputStream is = getClass().getResourceAsStream("welcome.html");
		return StringUtil.fromInputStream(is);
	}

	private OAIPMHtype createResponseSkeleton()
	{
		OAIPMHtype response = OAI_FACTORY.createOAIPMHtype();
		response.setResponseDate(newXMLGregorianCalendar());
		response.setRequest(echoRequest());
		return response;
	}

	private RequestType echoRequest()
	{
		RequestType echo = OAI_FACTORY.createRequestType();
		String requestUrl = uriInfo.getAbsolutePath().toString();
		echo.setValue(requestUrl);
		echo.setVerb(oaiRequest.getVerb());
		echo.setMetadataPrefix(oaiRequest.getMetadataPrefix());
		echo.setFrom(getArg(FROM));
		echo.setUntil(getArg(UNTIL));
		echo.setSet(oaiRequest.getSet());
		echo.setIdentifier(oaiRequest.getIdentifier());
		echo.setResumptionToken(oaiRequest.getResumptionToken());
		return echo;
	}

	private String getArg(Argument arg)
	{
		String s = uriInfo.getQueryParameters().getFirst(arg.param());
		if (s == null || s.length() == 0)
			return null;
		return s;
	}

	private static XMLGregorianCalendar newXMLGregorianCalendar()
	{
		try {
			XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					new GregorianCalendar());
			xgc.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
			// Hack:
			xgc.setTimezone(0);
			return xgc.normalize();
		}
		catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
