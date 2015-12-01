package nl.naturalis.oaipmh.rest;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static org.openarchives.oai._2.VerbType.GET_RECORD;
import static org.openarchives.oai._2.VerbType.IDENTIFY;
import static org.openarchives.oai._2.VerbType.LIST_IDENTIFIERS;
import static org.openarchives.oai._2.VerbType.LIST_METADATA_FORMATS;
import static org.openarchives.oai._2.VerbType.LIST_RECORDS;
import static org.openarchives.oai._2.VerbType.LIST_SETS;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import nl.naturalis.oaipmh.RepositoryFactory;
import nl.naturalis.oaipmh.RequestBuilder;
import nl.naturalis.oaipmh.api.IRepository;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;

import org.domainobject.util.ExceptionUtil;
import org.domainobject.util.StringUtil;
import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.VerbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST resource for all OAI-PMH requests.
 * 
 * @author Ayco Holleman
 *
 */
@Path("/")
public class OAIPMHResource {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OAIPMHResource.class);

	@Context
	private HttpServletRequest request;
	@Context
	private UriInfo uriInfo;

	/**
	 * Handles an OAI-PMH request for the specified OAI repository.
	 * 
	 * @param repoName
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{repo}")
	public Response handleRequest(@PathParam("repo") String repoName)
	{
		try {
			IRepository repo = RepositoryFactory.getInstance().create(repoName);
			RequestBuilder requestBuilder = RequestBuilder.newInstance();
			requestBuilder.setResumptionTokenParser(repo.getResumptionTokenParser());
			OAIPMHRequest oaiRequest = requestBuilder.build(uriInfo);
			OAIPMHtype oaiResponse = createResponseSkeleton(oaiRequest);
			if (requestBuilder.getErrors().size() != 0) {
				oaiResponse.getError().addAll(requestBuilder.getErrors());
				return RESTUtil.jaxbResponse(oaiResponse);
			}
			setPayload(oaiResponse, oaiRequest, repo);
			List<OAIPMHerrorType> errors = repo.getErrors();
			if (errors != null && errors.size() != 0)
				oaiResponse.getError().addAll(errors);
			return RESTUtil.jaxbResponse(oaiResponse);
		}
		catch (Throwable t) {
			return RESTUtil.serverError(ExceptionUtil.rootStackTrace(t));
		}
	}

	private static void setPayload(OAIPMHtype response, OAIPMHRequest request, IRepository repo)
			throws RepositoryException
	{
		VerbType verb = request.getVerb();
		if (verb == LIST_RECORDS)
			response.setListRecords(repo.listRecords(request));
		else if (verb == GET_RECORD)
			response.setGetRecord(repo.getRecord(request));
		else if (verb == LIST_IDENTIFIERS)
			response.setListIdentifiers(repo.listIdentifiers(request));
		else if (verb == IDENTIFY)
			response.setIdentify(repo.identify(request));
		else if (verb == LIST_SETS)
			response.setListSets(repo.listSets(request));
		else if (verb == LIST_METADATA_FORMATS)
			response.setListMetadataFormats(repo.listMetaDataFormats(request));
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

}
