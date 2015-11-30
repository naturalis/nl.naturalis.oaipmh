package nl.naturalis.oaipmh.api;

import java.util.List;

import org.openarchives.oai._2.AboutType;
import org.openarchives.oai._2.GetRecordType;
import org.openarchives.oai._2.IdentifyType;
import org.openarchives.oai._2.ListMetadataFormatsType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.ListSetsType;

/**
 * Life-cycle interface for OAI-PMH service requests. For each request the REST
 * layer cycles through the following calls against the repository:
 * <ol>
 * <li>If it is one of the ListXXX requests (e.g. ListRecords), call
 * {@link #getResumptionTokenReader()} to decompose the resumption token into
 * its constituent parts.
 * <li>Call one of the five methods that provide the actual response data.
 * <li>If it is one of the ListXXX requests, call
 * {@link #getResumptionTokenWriter()} to generate a resumption token for the
 * next request.
 * <li>Call {@link #getAbout()} to generate an &lt;about&gt; element.
 * <li>Call {@link #getErrors()} to generate the &lt;error&gt; elements.
 * </ol>
 * 
 * @author Ayco Holleman
 *
 */
public interface IRepository {

	IResumptionTokenReader getResumptionTokenReader();

	GetRecordType getRecord(OAIPMHRequest request) throws RepositoryException;

	ListRecordsType listRecords(OAIPMHRequest request) throws RepositoryException;

	ListMetadataFormatsType listMetaDataFormats(OAIPMHRequest request) throws RepositoryException;

	ListSetsType listSets(OAIPMHRequest request) throws RepositoryException;

	IdentifyType identify(OAIPMHRequest request) throws RepositoryException;

	AboutType getAbout() throws RepositoryException;

	List<OAIPMHError> getErrors();

}
