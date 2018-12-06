package nl.naturalis.oaipmh.api;

import nl.naturalis.oaipmh.api.util.ResumptionToken;

/**
 * Interface defining the capacity to compose a resumption token from the
 * information in an {@link OAIPMHRequest} instance. This interface is here only
 * for completeness. OAI repositories don't ever need to implement it, even if
 * they store more and/or other information in a resumption token than the
 * {@link ResumptionToken default implementation} (in which case they
 * <i>would</i> have to provide an implementation for
 * {@link IResumptionTokenParser}). This is because, while REST layer does need
 * to know how to parse a resumption token, generating one is left to the OAI
 * repository itself. The REST layer doesn't need to know how the repository
 * does this. Nevertheless, classes that implement
 * {@link IResumptionTokenParser} should probably also implement
 * {@link IResumptionTokenGenerator} (the {@link ResumptionToken default
 * implementation} does), because parsing a resumption token naturally mirrors
 * generating one.
 * 
 * @author Ayco Holleman
 *
 */
public interface IResumptionTokenGenerator {

	/**
	 * Generate a resumption token for the request <i>following</i> the
	 * specified request. For example, assuming the repository is configured to
	 * hand out 50 records per request and the specified request's
	 * {@link OAIPMHRequest#getPage() record offset} is 300, then a resumption
	 * token must be generated with a record offset of 350.
	 * 
	 * @param request
	 * @return
	 */
	String compose(OAIPMHRequest request);

}
