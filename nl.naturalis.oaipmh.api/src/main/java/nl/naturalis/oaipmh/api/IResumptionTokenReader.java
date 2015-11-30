package nl.naturalis.oaipmh.api;

public interface IResumptionTokenReader {

	/**
	 * Decompose the specified resumption token into its constituent parts and
	 * use these to create and return a new {@link OAIPMHRequest} instance.
	 * 
	 * @param resumptionToken
	 * @return
	 */
	OAIPMHRequest read(String resumptionToken) throws BadResumptionTokenException;

	/**
	 * Decompose the resumption token within the specified request object into
	 * its constituent parts and use these to set the other properties of the
	 * request object.
	 * 
	 * @param request
	 */
	void read(OAIPMHRequest request) throws BadResumptionTokenException;

}
