package nl.naturalis.oaipmh.api;

import java.util.List;

import nl.naturalis.oaipmh.api.util.ResumptionToken;

import org.openarchives.oai._2.GetRecordType;
import org.openarchives.oai._2.IdentifyType;
import org.openarchives.oai._2.ListIdentifiersType;
import org.openarchives.oai._2.ListMetadataFormatsType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.ListSetsType;
import org.openarchives.oai._2.OAIPMHerrorType;

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
	 * Allows the repository to prepare fore and itnitialize itself for a new
	 * OAI-PMH request.
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
	 * Implement the GetRecord protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	GetRecordType getRecord() throws RepositoryException;

	/**
	 * Implement the ListRecords protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListRecordsType listRecords() throws RepositoryException;

	/**
	 * Implement the ListIdentifiers protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListIdentifiersType listIdentifiers() throws RepositoryException;

	/**
	 * Implement the ListMetaDataFormats protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListMetadataFormatsType listMetaDataFormats() throws RepositoryException;

	/**
	 * Implement the ListSets protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListSetsType listSets() throws RepositoryException;

	/**
	 * Implement the Identify protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	IdentifyType identify() throws RepositoryException;

	/**
	 * Return OAI-PMH specific errors. Implementations may return {@code null}
	 * or an empty list to indicate that there were no errors. Returning a
	 * non-empty list while also return a non-{@code null} response for the
	 * applicable protocol request is also allowed.
	 * 
	 * @return
	 */
	List<OAIPMHerrorType> getErrors();

	/**
	 * Called just after a response has been sent back to the client. Allows
	 * repository implentations the close resources created for the request, if
	 * necessary.
	 */
	void done();

}