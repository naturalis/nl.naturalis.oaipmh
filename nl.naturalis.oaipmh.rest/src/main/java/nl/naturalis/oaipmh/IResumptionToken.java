package nl.naturalis.oaipmh;

public interface IResumptionToken {

	String compose(OAIPMHRequest request);

	void decompose(String token, OAIPMHRequest request) throws BadResumptionTokenException;

}
