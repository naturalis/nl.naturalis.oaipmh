package nl.naturalis.oaipmh.api;

import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.OAIPMHtype;

public interface IRepository {

	OAIPMHtype handleRequest(OAIPMHRequest request) throws RepositoryException;

	ListRecordsType listRecords(OAIPMHRequest request) throws RepositoryException;

}
