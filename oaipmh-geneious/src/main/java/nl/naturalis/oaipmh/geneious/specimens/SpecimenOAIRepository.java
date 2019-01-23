package nl.naturalis.oaipmh.geneious.specimens;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.openarchives.oai._2.OAIPMHtype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.geneious.GeneiousOAIRepository;

/**
 * OAI repository for specimens.
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenOAIRepository extends GeneiousOAIRepository {

  private static final Logger logger = LoggerFactory.getLogger(SpecimenOAIRepository.class);

  public SpecimenOAIRepository() {
    super();
  }

  @Override
  public void listRecords(OutputStream out) throws OAIPMHException, RepositoryException {
    if (logger.isDebugEnabled()) {
      logger.debug("Instantiating handler for ListRecords request");
    }
    SpecimenListRecordsHandler handler = new SpecimenListRecordsHandler(config, request);
    OAIPMHtype oaipmh = handler.handleRequest();
    if (logger.isTraceEnabled()) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
      stream(oaipmh, baos);
      logger.trace("Generated OAI-PMH:\n\n" + new String(baos.toByteArray()));
    }
    stream(oaipmh, out);
  }
}
