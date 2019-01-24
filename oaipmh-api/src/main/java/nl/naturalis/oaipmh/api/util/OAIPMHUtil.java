package nl.naturalis.oaipmh.api.util;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.RequestType;

import nl.naturalis.oaipmh.api.OAIPMHRequest;

/**
 * Utility class providing common functionality for OAI reposities.
 * 
 * @author Ayco Holleman
 *
 */
public class OAIPMHUtil {

  /**
   * Datetime pattern used for the OAI-PMH requests and responses (yyyy-MM-dd'T'HH:mm:ss'Z').
   */
  public static final String dateTimeFormat;

  /**
   * Datetime pattern that can optionally also be used for OAI-PMH requests (yyyy-MM-dd)
   */
  public static final String dateFormat;

  /**
   * Formats dates according to {@link #dateTimeFormat}.
   */
  public static final SimpleDateFormat dateTimeFormatter;

  /**
   * Formats dates according to {@link #dateFormat}.
   */
  public static final SimpleDateFormat dateFormatter;

  static {
    dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    dateFormat = "yyyy-MM-dd";
    dateTimeFormatter = new SimpleDateFormat(dateTimeFormat);
    dateFormatter = new SimpleDateFormat(dateFormat);
  }

  private OAIPMHUtil() {}

  /**
   * Creates the outer XML elements of an OAI-PMH response.
   * 
   * @param request
   * @return
   */
  public static OAIPMHtype createResponseSkeleton(OAIPMHRequest request) {
    OAIPMHtype response = ObjectFactories.oaiFactory.createOAIPMHtype();
    response.setResponseDate(newXMLGregorianCalendar());
    response.setRequest(echo(request));
    return response;
  }

  private static RequestType echo(OAIPMHRequest request) {
    RequestType echo = ObjectFactories.oaiFactory.createRequestType();
    echo.setValue(request.getRequestUri().toString());
    echo.setVerb(request.getVerb());
    if (request.getResumptionToken() != null) {
      echo.setResumptionToken(request.getResumptionToken());
    } else {
      echo.setMetadataPrefix(request.getMetadataPrefix());
      if (request.getFrom() != null) {
        if (request.getDateFormatFrom() == dateTimeFormat) {
          echo.setFrom(dateTimeFormatter.format(request.getFrom()));
        } else {
          echo.setFrom(dateFormatter.format(request.getFrom()));
        }
      }
      if (request.getUntil() != null) {
        if (request.getDateFormatUntil() == dateTimeFormat) {
          echo.setUntil(dateTimeFormatter.format(request.getUntil()));
        } else {
          echo.setUntil(dateFormatter.format(request.getUntil()));
        }
      }
      echo.setSet(request.getSet());
      echo.setIdentifier(request.getIdentifier());
    }
    return echo;
  }

  private static XMLGregorianCalendar newXMLGregorianCalendar() {
    try {
      XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
      xgc.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
      // Hack:
      xgc.setTimezone(0);
      return xgc.normalize();
    } catch (DatatypeConfigurationException e) {
      throw new RuntimeException(e);
    }
  }
}
