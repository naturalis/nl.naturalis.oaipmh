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
 * Life-cycle interface defining the capacities of an OAI repository. For each
 * OAI-PMH request the REST layer cycles through the following calls against the
 * repository:
 * <ol>
 * <li>If it is one of the ListXXX requests (e.g. ListRecords), call
 * {@link #getResumptionTokenParser()} to decompose the resumption token into
 * its constituent parts.
 * <li>Call one of the six protocol request implementations (depending on the
 * value of the verb parameter).
 * <li>If it is one of the ListXXX requests, call
 * {@link #getResumptionTokenWriter()} to generate a resumption token for the
 * next request.
 * <li>Call {@link #getErrors()} to generate the &lt;error&gt; elements.
 * </ol>
 * Besides implementing the interface methods, OAI repository implementations
 * <b>must</p> have a no-arg constructor.
 * 
 * @author Ayco Holleman
 *
 */
public interface IOAIRepository {

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
	GetRecordType getRecord(OAIPMHRequest request) throws RepositoryException;

	/**
	 * Implement the ListRecords protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListRecordsType listRecords(OAIPMHRequest request) throws RepositoryException;

	/**
	 * Implement the ListIdentifiers protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListIdentifiersType listIdentifiers(OAIPMHRequest request) throws RepositoryException;

	/**
	 * Implement the ListMetaDataFormats protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListMetadataFormatsType listMetaDataFormats(OAIPMHRequest request) throws RepositoryException;

	/**
	 * Implement the ListSets protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	ListSetsType listSets(OAIPMHRequest request) throws RepositoryException;

	/**
	 * Implement the Identify protocol request.
	 * 
	 * @param request
	 * @return
	 * @throws RepositoryException
	 */
	IdentifyType identify(OAIPMHRequest request) throws RepositoryException;

	/**
	 * Return OAI-PMH specific errors. Implementations may return {@code null}
	 * or an empty list to indicate that there were no errors. Returning a
	 * non-empty list while also return a non-{@code null} response for the
	 * applicable protocol request is also allowed.
	 * 
	 * @return
	 */
	List<OAIPMHerrorType> getErrors();

}
