package nl.naturalis.oaipmh;

import org.openarchives.oai._2.OAIPMHtype;

public interface IRepository {

	OAIPMHtype handleRequest(OAIPMHRequest request) throws ResourceException;

}
