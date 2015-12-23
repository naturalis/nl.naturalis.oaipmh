package nl.naturalis.oaipmh.api;

import java.io.OutputStream;

import nl.naturalis.oaipmh.api.util.ResumptionToken;

/**
 * <p>
 * Life-cycle interface defining the capacities of an OAI repository. This is
 * the only interface that OAI repositories <b>must</b> implement, although they
 * can optionally implement a few other interfaces (e.g.
 * {@link IResumptionTokenParser}). OAI repositories do not provide the full
 * OAI-PMH XML response. The REST layer sets up an XML skeleton and embeds the
 * response from methods like {@link #listRecords()} and {@link #getErrors()}
 * within the skeleton.
 * </p>
 * <h3>Life Cycle</h3>
 * <p>
 * For each OAI-PMH request the REST layer cycles through the following calls
 * against the repository:
 * <ol>
 * <li>Call {@link #getResumptionTokenParser()} to parse the value of the
 * resumptionToken query parameter (if present).
 * <li>Call the {@link #init(OAIPMHRequest) init} method, passing it an
 * {@link OAIPMHRequest} object.
 * <li>Call one of the six protocol request implementations (depending on the
 * value of the {@link Argument verb argument}).
 * <li>Call {@link #getErrors()} to generate the &lt;error&gt; elements.
 * <li>Call {@link #done()} to signify that a response has been sent back to
 * client and that the request cycle is complete.
 * </ol>
 * </p>
 * <p>
 * Besides implementing the interface methods, OAI repository implementations
 * <b>must</b> have a no-arg constructor.
 * </p>
 * 
 * @author Ayco Holleman
 *
 */
public interface IOAIRepository {

	/**
	 * Informs the OAI repository under which URL it operates. OAI repositories
	 * may need this information to generate xsi:schemaLocation attributes. The
	 * repository base URL is the base URL of the OAI-PMH REST service plus the
	 * subsequent path segment(s) identifying the repository itself. So, if the
	 * base URL of the OAI-PMH REST service would be
	 * {@code http://example.com/oaipmh}, then the <i>repository</i> base URL
	 * would be {@code http://example.com/oaipmh/repo-name} or
	 * {@code http://example.com/oaipmh/repo-group/repo-name}. For example, for
	 * Geneious DNA extracts the repository base URL would be
	 * {@code http://example.com/oaipmh/geneious/dna-extracts}. Clients could
	 * then make requests for custom XSDs at this location:
	 * {@code http://example.com/oaipmh/geneious/dna-extracts/xsd/<metadataPrefix>.xsd}
	 * (e.g.
	 * {@code http://example.com/oaipmh/geneious/dna-extracts/xsd/lims2.xsd}).
	 * This location therefore is a valid value for the xsi:schemaLocation
	 * attribute for the lims2 namespace.
	 * 
	 * @param url
	 */
	void setRepositoryBaseUrl(String url);

	/**
	 * Write the contents of the XML schema definition corresponding to the
	 * specified metadataPrefix to the specified output stream.
	 * 
	 * @param output
	 * @param metadataPrefix
	 */
	void getXSDForMetadataPrefix(OutputStream output, String metadataPrefix)
			throws RepositoryException;

	/**
	 * Prepare and initialize for a new OAI-PMH request.
	 * 
	 * @param request
	 */
	void init(OAIPMHRequest request);

	/**
	 * Provide a resumption token reader. Used to decompose a resumption token
	 * into its constituent parts. Implementations may return {@code null}, in
	 * which case the default {@link ResumptionToken parser} is used.
	 * 
	 * @return
	 */
	IResumptionTokenParser getResumptionTokenParser();

	/**
	 * Implement the GetRecord protocol request. The resulting OAI-PMH should be
	 * written to the specified output stream.
	 * 
	 * @param output
	 * @throws RepositoryException
	 */
	void getRecord(OutputStream output) throws OAIPMHException, RepositoryException;

	/**
	 * Implement the ListRecords protocol request. The resulting OAI-PMH should
	 * be written to the specified output stream.
	 * 
	 * @param output
	 * @throws RepositoryException
	 */
	void listRecords(OutputStream output) throws OAIPMHException, RepositoryException;

	/**
	 * Implement the ListIdentifiers protocol request. The resulting OAI-PMH
	 * should be written to the specified output stream.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	/**
	 * Implement the ListIdentifiers protocol request. The resulting OAI-PMH
	 * should be written to the specified output stream.
	 * 
	 * @param output
	 * @throws RepositoryException
	 */
	void listIdentifiers(OutputStream output) throws OAIPMHException, RepositoryException;

	/**
	 * Implement the ListMetaDataFormats protocol request. The resulting OAI-PMH
	 * should be written to the specified output stream.
	 * 
	 * @param output
	 * @throws RepositoryException
	 */
	void listMetaDataFormats(OutputStream output) throws OAIPMHException, RepositoryException;

	/**
	 * Implement the ListSets protocol request. The resulting OAI-PMH should be
	 * written to the specified output stream.
	 * 
	 * @param output
	 * @throws RepositoryException
	 */
	void listSets(OutputStream output) throws OAIPMHException, RepositoryException;

	/**
	 * Implement the Identify protocol request. The resulting OAI-PMH should be
	 * written to the specified output stream.
	 * 
	 * @param output
	 * @throws RepositoryException
	 */
	void identify(OutputStream output) throws OAIPMHException, RepositoryException;

	/**
	 * Called just after a response has been sent back to the client. Allows
	 * repository implentations the close resources created for the request, if
	 * necessary.
	 */
	void done();

}
