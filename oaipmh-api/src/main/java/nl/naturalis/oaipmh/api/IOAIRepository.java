package nl.naturalis.oaipmh.api;

import java.io.InputStream;
import java.io.OutputStream;

import nl.naturalis.oaipmh.api.util.ResumptionToken;
import nl.naturalis.oaipmh.util.ConfigObject;

/**
 * <p>
 * Call-back interface for OAI repositories. This is the only interface that OAI repositories must implement in order to plug into
 * the OAI-PMH REST framework, although they can optionally implement a few other interfaces (e.g.
 * {@link IResumptionTokenParser}).
 * </p>
 * <h3>Life Cycle</h3>
 * <p>
 * For each OAI-PMH request the REST layer cycles through the following calls against the repository:
 * <ol>
 * <li>Call {@link #setRepositoryBaseUrl(String) setRepositoryBaseUrl}. This tells the repository under which base URL it
 * operates. This allows the repository to generate XML with namespace declarations relative to the base URL. See also
 * {@link #getXSDForMetadataPrefix(OutputStream, String) getXSDForMetadataPrefix}.
 * <li>Call {@link #getResumptionTokenParser() getResumptionTokenParser}. This is a request to the repository to provide an
 * instance {@link IResumptionTokenParser}, so the REST layer can decompose and validate the resumption token (if present).
 * <li>Call {@link #init(OAIPMHRequest) init}. This allows the repository to initialize and prepare itself using the
 * {@link OAIPMHRequest} object passed to the {@code init} method. The {@code OAIPMHRequest} object contains all information for
 * the repository to carry out any of the six OAI-PMH protocol requests ({@link #getRecord(OutputStream) GetRecord},
 * {@link #listRecords(OutputStream) ListRecords}, {@link #listSets(OutputStream) ListSets}, etc.).
 * <li>Call one of the six protocol request implementations. The exact method being called depends on the value of the
 * {@link Argument verb argument} in the request URL.
 * <li>Call {@link #done() done} to signify that a response has been sent back to client and that the request cycle is complete.
 * </ol>
 * </p>
 * <p>
 * Besides implementing the interface methods, OAI repository implementations <b>must</b> have a no-arg constructor.
 * </p>
 * <h3>XML namespace prefixes</h3>
 * <p>
 * You will probably want to define your own namespace prefixes, like &#34;oai_dc:&#34; and &#34;abcd:&#34;, rather than use the
 * default prefixes generated by JAXB (&#34;ns1:&#34;, &#34;ns2:&#34;, etc.). This version of the library does not provide you
 * with the means to do this programmatically. Thefore, you must update the package-info.java file in your jAXB package. Include
 * an {@code xmlns} attribute within the &#64;XmlSchema annotation that you see in the package-info.java file. Note that <i>you
 * must do this each time you generate your JAXB classes using xjc</i> since this will also overwrite the package-info.java file.
 * Example:
 * 
 * <pre>
 * xmlns=&#64;XmlNs(namespaceURI=&#34;http://example.com/some&#34;,prefix=&#34;xyz&#34;)
 * 
 * <pre>
 * </p>
 * 
 * @author Ayco Holleman
 *
 */
public interface IOAIRepository {

  void setConfiguration(ConfigObject config);

  /**
   * Informs the OAI repository under which URL it operates. OAI repositories may need this information to generate
   * xsi:schemaLocation attributes. The repository base URL is the base URL of the OAI-PMH REST service plus the subsequent path
   * segment(s) identifying the repository itself. So, if the base URL of the OAI-PMH REST service would be
   * {@code http://example.com/oaipmh}, then the <i>repository</i> base URL would be {@code http://example.com/oaipmh/repo-name} or
   * {@code http://example.com/oaipmh/repo-group/repo-name}. For example, for Geneious DNA extracts the repository base URL would be
   * {@code http://example.com/oaipmh/geneious/dna-extracts}. Clients could then make requests for custom XSDs at this location:
   * {@code http://example.com/oaipmh/geneious/dna-extracts/xsd/<metadataPrefix>.xsd} (e.g.
   * {@code http://example.com/oaipmh/geneious/dna-extracts/xsd/lims2.xsd}). This location therefore is a valid value for the
   * xsi:schemaLocation attribute for the lims2 namespace.
   * 
   * @param url
   */
  void setRepositoryBaseUrl(String url);

  /**
   * Provides an {@link InputStream} from which to read the XML schema definition corresponding to the specified metadataPrefix.
   * This is especially meant to clients about custom XML schemas within the oai_dc element.
   * 
   * @param output
   * @param metadataPrefix
   */
  InputStream getXSDForMetadataPrefix(String metadataPrefix) throws RepositoryException;

  /**
   * Prepare and initialize for a new OAI-PMH request.
   * 
   * @param request
   */
  void init(OAIPMHRequest request);

  /**
   * Provide a resumption token reader. Used to decompose a resumption token into its constituent parts. Implementations may return
   * {@code null}, in which case the default {@link ResumptionToken parser} is used.
   * 
   * @return
   */
  IResumptionTokenParser getResumptionTokenParser();

  /**
   * Implement the GetRecord protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param output
   * @throws RepositoryException
   */
  void getRecord(OutputStream output) throws OAIPMHException, RepositoryException;

  /**
   * Implement the ListRecords protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param output
   * @throws RepositoryException
   */
  void listRecords(OutputStream output) throws OAIPMHException, RepositoryException;

  /**
   * Implement the ListIdentifiers protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param request
   * @return
   * @throws RepositoryException
   */
  /**
   * Implement the ListIdentifiers protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param output
   * @throws RepositoryException
   */
  void listIdentifiers(OutputStream output) throws OAIPMHException, RepositoryException;

  /**
   * Implement the ListMetaDataFormats protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param output
   * @throws RepositoryException
   */
  void listMetaDataFormats(OutputStream output) throws OAIPMHException, RepositoryException;

  /**
   * Implement the ListSets protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param output
   * @throws RepositoryException
   */
  void listSets(OutputStream output) throws OAIPMHException, RepositoryException;

  /**
   * Implement the Identify protocol request. The resulting OAI-PMH should be written to the specified output stream.
   * 
   * @param output
   * @throws RepositoryException
   */
  void identify(OutputStream output) throws OAIPMHException, RepositoryException;

  /**
   * Called just after a response has been sent back to the client. Allows repository implentations the close resources created for
   * the request, if necessary.
   */
  void done();

}
