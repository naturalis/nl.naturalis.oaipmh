package nl.naturalis.oaipmh.geneious.extracts;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.openarchives.oai._2.OAIPMHtype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.geneious.GeneiousOAIRepository;

/**
 * OAI repository for DNA extracts.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractOAIRepository extends GeneiousOAIRepository {

  private static final Logger logger = LoggerFactory.getLogger(DnaExtractOAIRepository.class);

  public DnaExtractOAIRepository() {
    super();
  }

  @Override
  public void listRecords(OutputStream out) throws OAIPMHException, RepositoryException {
    if (logger.isDebugEnabled()) {
      logger.debug("Instantiating handler for ListRecords request");
    }
    DnaExtractListRecordsHandler handler = new DnaExtractListRecordsHandler(config, request);
    OAIPMHtype oaipmh = handler.handleRequest();
    if (logger.isTraceEnabled()) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
      stream(oaipmh, baos);
      String output = new String(baos.toByteArray());
      logger.trace("Generated OAI-PMH:\n{}", output);
    }
    stream(oaipmh, out);
  }

}
