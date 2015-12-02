package nl.naturalis.oaipmh.api;

import nl.naturalis.oaipmh.api.util.ResumptionToken;

/**
 * Interface defining the capacity to compose a resumption token from the
 * information in an {@link OAIPMHRequest} instance. This interface is here only
 * for completeness, because while the REST layer decomposes the resumption
 * token, generating one is left to the OAI repository. The REST layer doesn't
 * need to be aware of how this is done by the reposiotry. Nevertheless, classes
 * that implement {@link IResumptionTokenParser} should proably also implement
 * {@link IResumptionTokenGenerator} (the {@link ResumptionToken default
 * implementation} does), because parsing a resumption token mirrors generating
 * one.
 * 
 * @author Ayco Holleman
 *
 */
public interface IResumptionTokenGenerator {

	/**
	 * Generate a resumption token for the request <i>following</i> the
	 * specified request. For example, if the specified request's
	 * {@link OAIPMHRequest#getCursor() page} is 7, then a resumption token must
	 * be generated that retrieves page 8 when a client uses it for his next
	 * request.
	 * 
	 * @param request
	 * @return
	 */
	String write(OAIPMHRequest request);

}
