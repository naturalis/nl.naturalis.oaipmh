package nl.naturalis.lims2.oaipmh.extracts;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import nl.naturalis.lims2.oaipmh.Lims2OAIRepository;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.RepositoryException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openarchives.oai._2.OAIPMHtype;

/**
 * OAI repository for DNA extracts.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractOAIRepository extends Lims2OAIRepository {

	private static final Logger logger = LogManager.getLogger(DnaExtractOAIRepository.class);

	public DnaExtractOAIRepository()
	{
		super();
	}

	@Override
	public void listRecords(OutputStream out) throws OAIPMHException, RepositoryException
	{
		logger.info("Instantiating handler for ListRecords request");
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
