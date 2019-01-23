package nl.naturalis.oaipmh.rest;

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
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.api.XSDNotFoundException;
import nl.naturalis.oaipmh.util.IOUtil;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static nl.naturalis.oaipmh.rest.RESTUtil.plainTextResponse;
import static nl.naturalis.oaipmh.rest.RESTUtil.serverError;
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
    InputStream in = getClass().getResourceAsStream("welcome.html");
    String s = new String(IOUtil.readAllBytes(in));
    return s;
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
      final RepositoryFactory factory = RepositoryFactory.getInstance();
      final IOAIRepository repository = factory.build(repoGroup, repoName);
      return xmlResponse(out -> {
        try {
          repository.getXSDForMetadataPrefix(out, prefix);
        } catch (XSDNotFoundException e) {
          throw new WebApplicationException(plainTextResponse(404, e.getMessage()));
        } catch (RepositoryException e) {
          throw new WebApplicationException(serverError(e));
        }
      });
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

  private Response handle(String repoGroup, String repoName) {
    logRequest(repoGroup, repoName);
    try {
      RepositoryFactory repoFactory = RepositoryFactory.getInstance();
      IOAIRepository repository = repoFactory.build(repoGroup, repoName);
      repository.setRepositoryBaseUrl(getRepoBaseURL(repoGroup, repoName));
      RequestBuilder requestBuilder = RequestBuilder.newInstance();
      requestBuilder.setResumptionTokenParser(repository.getResumptionTokenParser());
      OAIPMHRequest request = requestBuilder.build(uriInfo);
      if (requestBuilder.getErrors().size() != 0) {
        OAIPMHtype skeleton = createResponseSkeleton(request);
        skeleton.getError().addAll(requestBuilder.getErrors());
        return xmlResponse(skeleton);
      }
      if (logger.isDebugEnabled()) {
        logger.debug("Sending request to OAI repository");
      }
      repository.init(request);
      return new OAIPMHStream(request, repository).stream();
    } catch (Throwable t) {
      return serverError(t);
    }
  }

  private String getRepoBaseURL(String repoGroup, String repoName) {
    String url = Registry.getInstance().getConfig().get("baseUrl");
    if (url == null) {
      url = uriInfo.getBaseUri().toString();
    }
    StringBuilder sb = new StringBuilder(100);
    sb.append(url);
    if (!url.endsWith("/"))
      sb.append('/');
    sb.append(repoGroup);
    if (repoName != null)
      sb.append('/').append(repoName);
    sb.append('/');
    return sb.toString();
  }

  private static void logRequest(String repoGroup, String repoName) {
    if (logger.isDebugEnabled()) { // Make start of request easy to find in log file
      logger.debug("***** [ NEW OAI-PMH REQUEST ] *****");
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
