package nl.naturalis.oaipmh.api.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.naturalis.oaipmh.api.Argument;
import nl.naturalis.oaipmh.api.BadResumptionTokenException;
import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.IResumptionTokenGenerator;
import nl.naturalis.oaipmh.api.IResumptionTokenParser;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.util.BeanPrinter;
import nl.naturalis.oaipmh.util.StringUtil;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;

/**
 * Default implementation of {@link IResumptionTokenParser} and {@link IResumptionTokenGenerator}. This implementation is propably suitable
 * for most {@link IOAIRepository} implementations. This implementation encodes/decodes the following information into/from the resumption
 * token:
 * <ol>
 * <li>The {@link OAIPMHRequest#getFrom() from date}
 * <li>The {@link OAIPMHRequest#getUntil() until date}
 * <li>The requested {@link OAIPMHRequest#getSet() data set}
 * <li>The requested {@link OAIPMHRequest#getPage() page}
 * <li>The {@link OAIPMHRequest#getMetadataPrefix() metadataPrefix}
 * </ol>
 * 
 * @author Ayco Holleman
 *
 */
public class ResumptionToken implements IResumptionTokenParser, IResumptionTokenGenerator {

  private static final Logger logger = LogManager.getLogger(ResumptionToken.class);

  /*
   * Use capital 'X' as delimiter between in elements in the resumption token, so it blends in nicely with the elements but can never be
   * part of them. All elements are converted to base-36 numbers, containing any digit (0-9) and any lowercase letter (a-z).
   */
  private static final String DELIMITER = "X";
  private static final int RADIX = 36;

  private static final int FROM_PART = 0;
  private static final int UNTIL_PART = 1;
  private static final int SET_PART = 2;
  private static final int METADATA_PREFIX_PART = 3;
  private static final int PAGE_PART = 4;

  public ResumptionToken() {}

  @Override
  public OAIPMHRequest decompose(String resumptionToken) throws BadResumptionTokenException {
    OAIPMHRequest request = new OAIPMHRequest();
    request.setResumptionToken(resumptionToken);
    decompose(request);
    return request;
  }

  @Override
  public void decompose(OAIPMHRequest request) throws BadResumptionTokenException {
    if (request.getResumptionToken() == null) {
      logger.warn("No resumption token in request");
      return;
    }
    logger.info("Processing resumption token: " + request.getResumptionToken());
    String[] slices = StringUtil.split(request.getResumptionToken(), DELIMITER);
    if (logger.isDebugEnabled()) {
      logger.debug("Elements in resumption token:\n" + BeanPrinter.toString(slices));
    }
    if (slices.length != 5) {
      throw new BadResumptionTokenException(
          "Invalid number of elements in resumption token: " + slices.length);
    }
    request.setFrom(parseDate(FROM, slices[FROM_PART]));
    request.setUntil(parseDate(UNTIL, slices[UNTIL_PART]));
    if (slices[SET_PART].length() != 0) {
      BigInteger bi = new BigInteger(slices[SET_PART], RADIX);
      String set = new String(bi.toByteArray());
      request.setSet(set);
    }
    if (slices[METADATA_PREFIX_PART].length() != 0) {
      BigInteger bi = new BigInteger(slices[METADATA_PREFIX_PART], RADIX);
      String prefix = new String(bi.toByteArray());
      request.setMetadataPrefix(prefix);
    }
    try {
      int page = Integer.parseInt(slices[PAGE_PART], RADIX);
      request.setPage(page);
    } catch (NumberFormatException e) {
      String fmt = "Failed to extract page from resumption token (bad number: \"%s\")";
      String msg = String.format(fmt, slices[3]);
      throw new BadResumptionTokenException(msg);
    }
  }

  @Override
  public String compose(OAIPMHRequest request) {
    logger.debug("Generating resumption token for next request");
    String[] parts = new String[5];
    parts[PAGE_PART] = Integer.toString((request.getPage() + 1), RADIX);
    if (request.getFrom() != null) {
      long from = request.getFrom().getTime();
      parts[FROM_PART] = Long.toString(from, RADIX);
    }
    if (request.getUntil() != null) {
      long until = request.getUntil().getTime();
      parts[UNTIL_PART] = Long.toString(until, RADIX);
    }
    if (request.getSet() != null) {
      byte[] bytes = request.getSet().getBytes();
      BigInteger bi = new BigInteger(bytes);
      parts[SET_PART] = bi.toString(RADIX);
    }
    if (request.getMetadataPrefix() != null) {
      byte[] bytes = request.getMetadataPrefix().getBytes();
      BigInteger bi = new BigInteger(bytes);
      parts[METADATA_PREFIX_PART] = bi.toString(RADIX);
    }
    String token = Arrays.stream(parts).collect(Collectors.joining(DELIMITER));
    logger.debug("Resumption token for next request: " + token);
    return token;
  }

  private static Date parseDate(Argument arg, String value) throws BadResumptionTokenException {
    if (value.length() == 0)
      return null;
    try {
      return new Date(Long.parseLong(value, RADIX));
    } catch (NumberFormatException e) {
      String fmt = "Failed to extract %s argument from resumption token (bad number: \"%s\")";
      String msg = String.format(fmt, arg, value);
      throw new BadResumptionTokenException(msg);
    }

  }
}
