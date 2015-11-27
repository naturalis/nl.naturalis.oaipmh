package nl.naturalis.oaipmh;

import nl.naturalis.oaipmh.api.OAIPMHRequest;

public interface IResumptionToken {

	String compose(OAIPMHRequest request);

	void decompose(String token, OAIPMHRequest request) throws BadResumptionTokenException;

}
