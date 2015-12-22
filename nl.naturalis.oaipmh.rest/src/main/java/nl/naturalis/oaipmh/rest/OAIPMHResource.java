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
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.VerbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * REST resource handling all OAI-PMH requests for all OAI repositories. Each
 * OAI repository is accessed through a sub-resource of this REST resource. In
 * other words, if the base URL of this REST service is
 * http://example.com/oaipmh, then http://example.com/oaipmh/geneious will
 * access the geneious repository and http://example.com/oaipmh/medialib will
 * access the medialib repository.
 * </p>
 * <h3>Grouping repositories</h3>
 * <p>
 * You can group similar repositories by using an extra path segment, for
 * example as follows:<br>
 * http://example.com/oaipmh/geneious/specimens<br>
 * http://example.com/oaipmh/geneious/dna-extracts<br>
 * http://example.com/oaipmh/geneious/dna-slides<br>
 * This is explained in the comments for {@link RepositoryFactory}.
 * </p>
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
	 * @param repoDescriptor
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{descriptor}")
	public Response handleRequest(@PathParam("descriptor") String repoDescriptor)
	{
		return handle(repoDescriptor, null);
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{descriptor}/{name}")
	public Response handleRequest(@PathParam("descriptor") String repoDescriptor,
			@PathParam("name") String repoName)
	{
		return handle(repoDescriptor, repoName);
	}

	private Response handle(String repoDescriptor, String repoName)
	{
		try {
			IOAIRepository repo = RepositoryFactory.getInstance().create(repoDescriptor, repoName);
			RequestBuilder requestBuilder = RequestBuilder.newInstance();
			requestBuilder.setResumptionTokenParser(repo.getResumptionTokenParser());
			OAIPMHRequest oaiRequest = requestBuilder.build(uriInfo);
			repo.init(oaiRequest);
			OAIPMHtype oaiResponse = createResponseSkeleton(oaiRequest);
			if (requestBuilder.getErrors().size() != 0) {
				oaiResponse.getError().addAll(requestBuilder.getErrors());
				return RESTUtil.jaxbResponse(oaiResponse);
			}
			return RESTUtil.xmlResponse(getPayload(repo, oaiRequest.getVerb()));
		}
		catch (Throwable t) {
			return RESTUtil.serverError(ExceptionUtil.rootStackTrace(t));
		}
	}

	private static String getPayload(IOAIRepository repo, VerbType verb) throws RepositoryException
	{
		if (verb == LIST_RECORDS)
			return repo.listRecords();
		else if (verb == GET_RECORD)
			return repo.getRecord();
		else if (verb == LIST_IDENTIFIERS)
			return repo.listIdentifiers();
		else if (verb == IDENTIFY)
			return repo.identify();
		else if (verb == LIST_SETS)
			return repo.listSets();
		else if (verb == LIST_METADATA_FORMATS)
			return repo.listMetaDataFormats();
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

}
