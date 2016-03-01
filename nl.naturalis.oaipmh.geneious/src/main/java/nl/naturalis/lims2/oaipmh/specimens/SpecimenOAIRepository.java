package nl.naturalis.lims2.oaipmh.specimens;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import nl.naturalis.lims2.oaipmh.Lims2OAIRepository;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.RepositoryException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openarchives.oai._2.OAIPMHtype;

/**
 * OAI repository for specimens.
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenOAIRepository extends Lims2OAIRepository {

	private static final Logger logger = LogManager.getLogger(SpecimenOAIRepository.class);

	public SpecimenOAIRepository()
	{
		super();
	}

	@Override
	public void listRecords(OutputStream out) throws OAIPMHException, RepositoryException
	{
		logger.info("Instantiating handler for ListRecords request");
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
