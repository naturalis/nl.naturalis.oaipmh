package nl.naturalis.oaipmh.rest;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.VerbType;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;

import static org.openarchives.oai._2.VerbType.GET_RECORD;
import static org.openarchives.oai._2.VerbType.IDENTIFY;
import static org.openarchives.oai._2.VerbType.LIST_IDENTIFIERS;
import static org.openarchives.oai._2.VerbType.LIST_METADATA_FORMATS;
import static org.openarchives.oai._2.VerbType.LIST_RECORDS;
import static org.openarchives.oai._2.VerbType.LIST_SETS;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createOAIPMHSkeleton;
import static nl.naturalis.oaipmh.rest.RESTUtil.serverError;
import static nl.naturalis.oaipmh.rest.RESTUtil.xmlResponse;

/**
 * An implementation of {@link StreamingOutput StreamingOutput} that streams out the OAI-PMH coming back from an OAI repository.
 * 
 * @author Ayco Holleman
 *
 */
class OAIPMHStreamingOutput implements StreamingOutput {

  private final OAIPMHRequest request;
  private final IOAIRepository repo;

  OAIPMHStreamingOutput(OAIPMHRequest request, IOAIRepository repo) {
    this.request = request;
    this.repo = repo;
  }

  /**
   * Puts this {@code OAIPMHStream} on the HTTP response and returns the response.
   * 
   * @return
   */
  Response toResponse() {
    return xmlResponse(this);
  }

  @Override
  public void write(OutputStream out) throws IOException, WebApplicationException {
    VerbType verb = request.getVerb();
    try {
      if (verb == GET_RECORD) {
        repo.getRecord(out);
      } else if (verb == IDENTIFY) {
        repo.identify(out);
      } else if (verb == LIST_IDENTIFIERS) {
        repo.listIdentifiers(out);
      } else if (verb == LIST_METADATA_FORMATS) {
        repo.listMetaDataFormats(out);
      } else if (verb == LIST_RECORDS) {
        repo.listRecords(out);
      } else if (verb == LIST_SETS) {
        repo.listSets(out);
      }
    } catch (OAIPMHException e) {
      OAIPMHtype oaipmh = createOAIPMHSkeleton(request);
      oaipmh.getError().addAll(e.getErrors());
      throw new WebApplicationException(xmlResponse(oaipmh));
    } catch (RepositoryException e) {
      if (e.getCause() == null) {
        throw new WebApplicationException(serverError(e.getMessage()));
      }
      throw new WebApplicationException(serverError(e));
    } catch (Throwable t) {
      throw new WebApplicationException(serverError(t));
    }
  }

}
