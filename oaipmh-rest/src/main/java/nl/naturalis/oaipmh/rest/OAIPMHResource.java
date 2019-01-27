package nl.naturalis.oaipmh.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.openarchives.oai._2.OAIPMHtype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.XSDNotFoundException;
import nl.naturalis.oaipmh.util.IOUtil;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createOAIPMHSkeleton;
import static nl.naturalis.oaipmh.rest.RESTUtil.plainTextResponse;
import static nl.naturalis.oaipmh.rest.RESTUtil.serverError;
import static nl.naturalis.oaipmh.rest.RESTUtil.streamingResponse;
import static nl.naturalis.oaipmh.rest.RESTUtil.xmlResponse;

/**
 * <p>
 * REST resource handling all OAI-PMH requests for all OAI repositories. Each OAI repository is accessed through a sub-resource of
 * this REST resource. In other words, if the base URL of the OAI-PMH REST service would be http://example.com/oaipmh, then
 * http://example.com/oaipmh<b>/geneious</b> will access the geneious repository and http://example.com/oaipmh<b>/medialib</b>
 * will access the medialib repository.
 * </p>
 * <h3>Grouping repositories</h3>
 * <p>
 * You can group similar repositories by using an extra path segment, for example as follows:<br>
 * http://example.com/oaipmh/geneious/specimens<br>
 * http://example.com/oaipmh/geneious/dna-extracts<br>
 * http://example.com/oaipmh/geneious/dna-slides<br>
 * The first path segment after the base URL then becomes the name of the repository group while the next path segment specifies
 * the name of the repository within that group. Apart from providing a nice URL structure, repository groups share a single
 * configuration file. This is explained in the comments for {@link RepositoryFactory}.
 * </p>
 * 
 * @author Ayco Holleman
 *
 */
@Path("/")
public class OAIPMHResource {

  private static final Logger logger = LoggerFactory.getLogger(OAIPMHResource.class);

  @Context
  private HttpServletRequest httpServletRequest;
  @Context
  private UriInfo uriInfo;

  /**
   * Show some welcome content.
   * 
   * @return
   */
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String welcome() {
    try (InputStream in = getClass().getResourceAsStream("welcome.html")) {
      return new String(IOUtil.readAllBytes(in));
    } catch (IOException e) {
      throw new WebApplicationException("Error closing input stream for welcome.html", e);
    }
  }

  /**
   * Returns the XSD for the specified metadataPrefix.
   * 
   * @param repoGroup
   * @param repoName
   * @param prefix
   * @return
   */
  @GET
  @Path("/{group}/{repo}/xsd/{prefix}.xsd")
  public Response getXSD(@PathParam("group") String repoGroup, @PathParam("repo") String repoName,
      @PathParam("prefix") final String prefix) {
    try {
      RepositoryFactory factory = RepositoryFactory.getInstance();
      IOAIRepository repository = factory.build(repoGroup, repoName);
      return streamingResponse(repository.getXSDForMetadataPrefix(prefix), MediaType.APPLICATION_XML);
    } catch (XSDNotFoundException e) {
      return plainTextResponse(404, e.getMessage());
    } catch (Throwable t) {
      return serverError(t);
    }
  }

  /**
   * Handles an OAI-PMH request for the specified OAI repository.
   * 
   * @param repo
   * @return
   */
  @GET
  @Path("/{repo}")
  public Response handleRequest(@PathParam("repo") String repo) {
    return handle(repo, null);
  }

  /**
   * Handles an OAI-PMH request for the specified OAI repository group and repository name.
   * 
   * @param repoGroup
   * @param repoName
   * @return
   */
  @GET
  @Path("/{group}/{repo}")
  public Response handleRequest(@PathParam("group") String repoGroup, @PathParam("repo") String repoName) {
    return handle(repoGroup, repoName);
  }

  /**
   * Intercepts and ignores requests for favicon in case we are running under "/".
   * 
   * @return
   */
  @GET
  @Path("/favicon.ico")
  public Response favicon() {
    // return Response.noContent().build();
    return streamingResponse(getClass().getResourceAsStream("/favicon.ico"), "image/x-icon");
  }

  private Response handle(String repoGroup, String repoName) {
    logRequest(repoGroup, repoName);
    RepositoryFactory repoFactory = RepositoryFactory.getInstance();
    IOAIRepository repo;
    try {
      repo = repoFactory.build(repoGroup, repoName);
    } catch (RepositoryInitializationException e) {
      return serverError(e.getMessage());
    }
    repo.setRepositoryBaseUrl(getRepoBaseUrl(repoGroup, repoName));
    RequestBuilder requestBuilder = RequestBuilder.newInstance();
    requestBuilder.setResumptionTokenParser(repo.getResumptionTokenParser());
    OAIPMHRequest request = requestBuilder.build(uriInfo);
    if (requestBuilder.getErrors().size() != 0) {
      OAIPMHtype oaipmh = createOAIPMHSkeleton(request);
      oaipmh.getError().addAll(requestBuilder.getErrors());
      return xmlResponse(oaipmh);
    }
    logger.debug("Sending request to OAI repository");
    repo.init(request);
    return new OAIPMHStreamingOutput(request, repo).toResponse();
  }

  private String getRepoBaseUrl(String repoGroup, String repoName) {
    String url = Registry.getInstance().getConfig().get("baseUrl");
    if (url == null) {
      url = uriInfo.getBaseUri().toString();
    }
    StringBuilder sb = new StringBuilder(100);
    sb.append(url);
    if (!url.endsWith("/")) {
      sb.append('/');
    }
    sb.append(repoGroup);
    if (repoName != null) {
      sb.append('/').append(repoName);
    }
    sb.append('/');
    return sb.toString();
  }

  private static void logRequest(String repoGroup, String repoName) {
    if (logger.isDebugEnabled()) {
      logger.debug("**** [ NEW OAI-PMH REQUEST ] ****");
    }
    String msg;
    if (repoName == null) {
      msg = String.format("Receiving request for OAI repository %s", repoGroup);
    } else {
      msg = String.format("Receiving request for OAI repository %s/%s", repoGroup, repoName);
    }
    logger.info(msg);
  }

}
