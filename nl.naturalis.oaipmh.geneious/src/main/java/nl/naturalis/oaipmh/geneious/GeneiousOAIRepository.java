package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.GENEIOUS_XMLNS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.IResumptionTokenParser;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.api.XSDNotFoundException;
import nl.naturalis.oaipmh.api.util.OAIPMHStreamer;

import org.domainobject.util.ConfigObject;
import org.domainobject.util.IOUtil;
import org.openarchives.oai._2.OAIPMHtype;

/**
 * Abstract base class for LIMS2/Geneious OAI repositories. Provides the
 * implementation of
 * {@link IOAIRepository#getXSDForMetadataPrefix(OutputStream, String)
 * getXSDForMetadataPrefix} and {@link IOAIRepository#init(OAIPMHRequest) init}
 * and provides no-op implementations of the other methods defined by
 * {@link IOAIRepository}.
 * 
 * @author Ayco Holleman
 *
 */
public abstract class GeneiousOAIRepository implements IOAIRepository {

	protected ConfigObject config;
	protected OAIPMHRequest request;
	protected String repoBaseURL;

	public GeneiousOAIRepository()
	{
	}

	public void setConfiguration(ConfigObject config)
	{
		this.config = config;
	}

	@Override
	public void setRepositoryBaseUrl(String url)
	{
		this.repoBaseURL = url;
	}

	@Override
	public void getXSDForMetadataPrefix(OutputStream out, String prefix) throws RepositoryException
	{
		if (prefix.equals("geneious")) {
			InputStream in = getClass().getResourceAsStream("/geneious.xsd");
			if (in == null) {
				throw new XSDNotFoundException(prefix);
			}
			IOUtil.pipe(in, out, 2048);
			try {
				in.close();
			}
			catch (IOException e) {
				throw new RepositoryException(e);
			}
		}
		else {
			throw new XSDNotFoundException(prefix);
		}
	}

	@Override
	public void init(OAIPMHRequest request)
	{
		this.request = request;
	}

	@Override
	public IResumptionTokenParser getResumptionTokenParser()
	{
		return null;
	}

	@Override
	public void getRecord(OutputStream out) throws OAIPMHException, RepositoryException
	{
		throw new RepositoryException("GetRecord request not implemented yet");
	}

	@Override
	public void listIdentifiers(OutputStream out) throws OAIPMHException, RepositoryException
	{
		throw new RepositoryException("ListIdentifiers request not implemented yet");
	}

	@Override
	public void listSets(OutputStream out) throws OAIPMHException, RepositoryException
	{
		throw new RepositoryException("ListSets request not implemented yet");
	}

	@Override
	public void identify(OutputStream out) throws OAIPMHException, RepositoryException
	{
		throw new RepositoryException("Identify request not implemented yet");
	}

	@Override
	public void listMetaDataFormats(OutputStream out) throws OAIPMHException, RepositoryException
	{
		throw new RepositoryException("ListMetaDataFormats request not implemented yet");
	}

	@Override
	public void done()
	{
	}

	/**
	 * Writes the OAI-PMH to the specified output stream.
	 * 
	 * @param oaipmh
	 * @param out
	 * @throws RepositoryException
	 */
	protected void stream(OAIPMHtype oaipmh, OutputStream out) throws RepositoryException
	{
		OAIPMHStreamer streamer = new OAIPMHStreamer();
		streamer.setRootElement(oaipmh);
		streamer.addJaxbPackage("nl.naturalis.oaipmh.geneious.jaxb");
		String schemaLocation = repoBaseURL + "xsd/geneious.xsd";
		streamer.addSchemaLocation(GENEIOUS_XMLNS, schemaLocation);
		try {
			streamer.stream(out);
		}
		catch (JAXBException e) {
			throw new RepositoryException(e);
		}
	}

}
