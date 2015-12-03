package nl.naturalis.oaipmh.api;

import nl.naturalis.oaipmh.api.util.ResumptionToken;

/**
 * Interface defining the capacity to decompose a resumption token into its
 * constitutent parts. Repositories that have special ways of paging through
 * large result sets can implement this interface and return an instance of it
 * in their implementation of {@link IOAIRepository#getResumptionTokenParser()}.
 * This instance will then be used to decompose the resumption token. Otherwise
 * the REST layer will use the {@link ResumptionToken default implementation}.
 * 
 * @author Ayco Holleman
 *
 */
public interface IResumptionTokenParser {

	/**
	 * Decompose the specified resumption token into its constituent parts and
	 * create a new {@link OAIPMHRequest} instance from them.
	 * 
	 * @param resumptionToken
	 * @return
	 */
	OAIPMHRequest decompose(String resumptionToken) throws BadResumptionTokenException;

	/**
	 * Decompose the resumption token within the specified request object into
	 * its constituent parts and use these to set the other properties in the
	 * request object.
	 * 
	 * @param request
	 */
	void decompose(OAIPMHRequest request) throws BadResumptionTokenException;

}
