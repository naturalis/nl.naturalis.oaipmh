package nl.naturalis.oaipmh.api;

import org.openarchives.oai._2.GetRecordType;
import org.openarchives.oai._2.IdentifyType;
import org.openarchives.oai._2.ListMetadataFormatsType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.ListSetsType;

public interface IRepository {

	GetRecordType getRecord(OAIPMHRequest request) throws RepositoryException;

	ListRecordsType listRecords(OAIPMHRequest request) throws RepositoryException;

	ListMetadataFormatsType listMetaDataFormats(OAIPMHRequest request) throws RepositoryException;

	ListSetsType listSets(OAIPMHRequest request) throws RepositoryException;

	IdentifyType identify(OAIPMHRequest request) throws RepositoryException;

}
