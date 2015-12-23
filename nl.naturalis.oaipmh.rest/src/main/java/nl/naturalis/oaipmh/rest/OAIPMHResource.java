package nl.naturalis.oaipmh.rest;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static nl.naturalis.oaipmh.rest.RESTUtil.serverError;
import static nl.naturalis.oaipmh.rest.RESTUtil.xmlResponse;
import static org.openarchives.oai._2.VerbType.GET_RECORD;
import static org.openarchives.oai._2.VerbType.IDENTIFY;
import static org.openarchives.oai._2.VerbType.LIST_IDENTIFIERS;
import static org.openarchives.oai._2.VerbType.LIST_METADATA_FORMATS;
import static org.openarchives.oai._2.VerbType.LIST_RECORDS;
import static org.openarchives.oai._2.VerbType.LIST_SETS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.api.XSDNotFoundException;

import org.domainobject.util.IOUtil;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.VerbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * REST resource handling all OAI-PMH requests for all OAI repositories. Each
 * OAI repository is accessed through a sub-resource of this REST resource. In
 * other words, if the base URL of the OAI-PMH REST service is
 * http://example.com/oaipmh, then http://example.com/oaipmh<b>/geneious</b>
 * will access the geneious repository and
 * http://example.com/oaipmh<b>/medialib</b> will access the medialib
 * repository.
 * </p>
 * <h3>Grouping repositories</h3>
 * <p>
 * You can group similar repositories by using an extra path segment, for
 * example as follows:<br>
 * http://example.com/oaipmh/geneious/specimens<br>
 * http://example.com/oaipmh/geneious/dna-extracts<br>
 * http://example.com/oaipmh/geneious/dna-slides<br>
 * The first path segment after the base URL then becomes the name of the group
 * while the next path segment specifies the name of the repository. Apart from
 * providing a nice URL structure, repository groups share a single
 * configuration file. This is explained in the comments for
 * {@link RepositoryFactory}.
 * </p>
 * 
 * @author Ayco Holleman
 *
 */
@Path("/")
public class OAIPMHResource {

	private static final Logger logger = LoggerFactory.getLogger(OAIPMHResource.class);

	@Context
	private HttpServletRequest request;
	@Context
	private UriInfo uriInfo;

	/**
	 * Show some welcome content.
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@SuppressWarnings("static-method")
	public StreamingOutput welcome()
	{
		return new StreamingOutput() {

			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException
			{
				InputStream in = getClass().getResourceAsStream("welcome.html");
				IOUtil.pipe(in, out, 2048);
			}
		};
	}

	@GET
	@Path("/{group}/{repo}/xsd/{prefix}.xsd")
	@SuppressWarnings("static-method")
	public Response getXSD(@PathParam("group") String repoGroup,
			@PathParam("repo") String repoName, @PathParam("prefix") String prefix)
	{
		try {
			RepositoryFactory rf = RepositoryFactory.getInstance();
			IOAIRepository repo = rf.create(repoGroup, repoName);
			return xmlResponse(new StreamingOutput() {

				@Override
				public void write(OutputStream out) throws IOException, WebApplicationException
				{
					try {
						repo.getXSDForMetadataPrefix(out, prefix);
					}
					catch (RepositoryException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
		catch (Throwable t) {
			if (t.getCause() instanceof XSDNotFoundException)
				return RESTUtil.plainTextResponse(404, t.getMessage());
			return RESTUtil.serverError(t);
		}
	}

	/**
	 * Handles an OAI-PMH request for the specified OAI repository.
	 * 
	 * @param repo
	 * @return
	 */
	@GET
	@POST
	@Path("/{repo}")
	public Response handleRequest(@PathParam("repo") String repo)
	{
		return handle(repo, null);
	}

	@GET
	@POST
	@Path("/{group}/{repo}")
	public Response handleRequest(@PathParam("group") String repoGroup,
			@PathParam("repo") String repoName)
	{
		return handle(repoGroup, repoName);
	}

	private Response handle(String repoGroup, String repoName)
	{
		String name = repoName == null ? repoGroup : repoName;
		logger.info("Receiving request for OAI repository \"" + name + "\"");
		try {
			RepositoryFactory rf = RepositoryFactory.getInstance();
			IOAIRepository repo = rf.create(repoGroup, repoName);
			repo.setRepositoryBaseUrl(getRepoBaseURL(repoGroup, repoName));
			RequestBuilder requestBuilder = RequestBuilder.newInstance();
			requestBuilder.setResumptionTokenParser(repo.getResumptionTokenParser());
			OAIPMHRequest oaiRequest = requestBuilder.build(uriInfo);
			repo.init(oaiRequest);
			OAIPMHtype skeleton = createResponseSkeleton(oaiRequest);
			if (requestBuilder.getErrors().size() != 0) {
				skeleton.getError().addAll(requestBuilder.getErrors());
				return xmlResponse(skeleton);
			}
			// try {
			return xmlResponse(getStream(skeleton, repo, oaiRequest.getVerb()));
			// }
			// catch (RuntimeException e) {
			// if (e.getCause().getClass() == OAIPMHException.class) {
			// OAIPMHException exc = (OAIPMHException) e.getCause();
			// skeleton.getError().addAll(exc.getErrors());
			// return xmlResponse(skeleton);
			// }
			// return serverError(e);
			// }
		}
		catch (Throwable t) {
			return serverError(t);
		}
	}

	private String getRepoBaseURL(String repoGroup, String repoName)
	{
		String url = uriInfo.getBaseUri().toString();
		StringBuilder sb = new StringBuilder(95);
		sb.append(url);
		if (!url.endsWith("/"))
			sb.append('/');
		sb.append(repoGroup);
		if (repoName != null)
			sb.append('/').append(repoName);
		sb.append('/');
		return sb.toString();
	}

	private static StreamingOutput getStream(OAIPMHtype skeleton, IOAIRepository repo, VerbType verb)
	{
		return new StreamingOutput() {

			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException
			{
				try {
					if (verb == GET_RECORD)
						repo.getRecord(out);
					else if (verb == IDENTIFY)
						repo.identify(out);
					else if (verb == LIST_IDENTIFIERS)
						repo.listIdentifiers(out);
					else if (verb == LIST_METADATA_FORMATS)
						repo.listMetaDataFormats(out);
					else if (verb == LIST_RECORDS)
						repo.listRecords(out);
					else if (verb == LIST_SETS)
						repo.listSets(out);
				}
				catch (OAIPMHException e) {
					skeleton.getError().addAll(e.getErrors());
					throw new WebApplicationException(xmlResponse(skeleton));
				}
				catch (RepositoryException e) {
					throw new WebApplicationException(e);
				}
			}
		};
	}

	@SuppressWarnings("serial")
	private static class RepoExceptionWrapper extends RuntimeException {

		RepoExceptionWrapper(Throwable e)
		{
			super(e);
		}

	}

}
