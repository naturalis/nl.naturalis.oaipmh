package nl.naturalis.oaipmh.api.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import nl.naturalis.oaipmh.api.BadResumptionTokenException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;

import org.junit.Test;

public class ResumptionTokenTest {

  /*
   * Tests resumption token generation by parsing the generated resumption token back into an OAIPMHRequest and ensuring it has the
   * same data. except for the page, which should be one higher than for the original request.
   * 
   * @throws BadResumptionTokenException
   */
  @Test
  public void testComposeAndDecompose() throws BadResumptionTokenException {
    OAIPMHRequest request = new OAIPMHRequest();
    request.setFrom(new Date(1000000L));
    request.setUntil(new Date(2000000L));
    request.setPage(4);
    request.setSet("LEPIDOPTERA");
    request.setMetadataPrefix("oai_dc");
    String token = new ResumptionToken().compose(request);
    OAIPMHRequest request2 = new ResumptionToken().decompose(token);
    assertEquals("01", request.getFrom(), request2.getFrom());
    assertEquals("02", request.getUntil(), request2.getUntil());
    assertEquals("03", request.getSet(), request2.getSet());
    assertEquals("04", request.getMetadataPrefix(), request2.getMetadataPrefix());
    assertEquals("05", request.getPage() + 1, request2.getPage());
    assertEquals(token, request2.getResumptionToken());
  }

}
