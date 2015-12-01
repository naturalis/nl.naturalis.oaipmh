package nl.naturalis.oaipmh.api;

/**
 * Interface definig the capacity to compose a resumption token from the
 * information in an {@link OAIPMHRequest} instance.
 * 
 * @author Ayco Holleman
 *
 */
public interface IResumptionTokenGenerator {

	/**
	 * Generate a resumption token for the request <i>following</i> the
	 * specified request. For example, if the specified request's
	 * {@link OAIPMHRequest#getPage() page} is 7, then a resumption token must
	 * be generated that retrieves page 8 when a client uses it for his next
	 * request.
	 * 
	 * @param request
	 * @return
	 */
	String write(OAIPMHRequest request);

}
