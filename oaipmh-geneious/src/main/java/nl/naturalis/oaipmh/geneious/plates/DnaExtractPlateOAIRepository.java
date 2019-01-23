package nl.naturalis.oaipmh.geneious.plates;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.openarchives.oai._2.OAIPMHtype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.geneious.GeneiousOAIRepository;

public class DnaExtractPlateOAIRepository extends GeneiousOAIRepository {

  private static final Logger logger = LoggerFactory.getLogger(DnaExtractPlateOAIRepository.class);

  public DnaExtractPlateOAIRepository() {
    super();
  }

  @Override
  public void listRecords(OutputStream out) throws OAIPMHException, RepositoryException {
    if (logger.isDebugEnabled()) {
      logger.debug("Instantiating handler for ListRecords request");
    }
    DnaExtractPlateListRecordsHandler handler;
    handler = new DnaExtractPlateListRecordsHandler(config, request);
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
