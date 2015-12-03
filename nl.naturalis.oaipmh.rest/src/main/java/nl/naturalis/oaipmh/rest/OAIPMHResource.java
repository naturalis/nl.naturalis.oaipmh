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

import nl.naturalis.oaipmh.api.IOAIRepository;
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
 * REST resource handling all OAI-PMH requests for all OAI repositories. Each
 * OAI repository is accessed through a sub-resource of this REST resource. In
 * other words, if the base URL of this REST service is http://example.com/home,
 * then http://example.com/home/repo1 will access the repo1 repository and
 * http://example.com/home/repo2 will access the repo2 repository.
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
			IOAIRepository repo = RepositoryFactory.getInstance().create(repoName);
			RequestBuilder requestBuilder = RequestBuilder.newInstance();
			requestBuilder.setResumptionTokenParser(repo.getResumptionTokenParser());
			OAIPMHRequest oaiRequest = requestBuilder.build(uriInfo);
			repo.init(oaiRequest);
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

	private static void setPayload(OAIPMHtype response, OAIPMHRequest request, IOAIRepository repo)
			throws RepositoryException
	{
		VerbType verb = request.getVerb();
		if (verb == LIST_RECORDS)
			response.setListRecords(repo.listRecords());
		else if (verb == GET_RECORD)
			response.setGetRecord(repo.getRecord());
		else if (verb == LIST_IDENTIFIERS)
			response.setListIdentifiers(repo.listIdentifiers());
		else if (verb == IDENTIFY)
			response.setIdentify(repo.identify());
		else if (verb == LIST_SETS)
			response.setListSets(repo.listSets());
		else if (verb == LIST_METADATA_FORMATS)
			response.setListMetadataFormats(repo.listMetaDataFormats());
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
