package nl.naturalis.oaipmh.api;

public interface IResumptionTokenWriter {

	/**
	 * Generate a resumption token for the request <i>following</i> the
	 * specified request. For example, if the specified request's
	 * {@link OAIPMHRequest#getPage() page} is 7, then a resumption token should
	 * be generated that retrieves page 8 when the client uses it for his next
	 * request.
	 * 
	 * @param request
	 * @return
	 */
	String write(OAIPMHRequest request);

}
