package nl.naturalis.oaipmh.rest;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.util.BeanPrinter;

import static nl.naturalis.oaipmh.util.ExceptionUtil.getRootCauseStackTrace;

/**
 * Provides common functionality for the REST service.
 * 
 * @author Ayco Holleman
 *
 */
public class RESTUtil {

  private static final Logger logger = LoggerFactory.getLogger(RESTUtil.class);

  private RESTUtil() {}

  /**
   * Generates an HTTP response with status 500 (INTERNAL SERVER ERROR) and the specified message in the response body. The content
   * type of the response body is set to text/plain.
   * 
   * @param message
   * @return
   */
  public static Response serverError(String message) {
    logger.error(message);
    message = "500 (INTERNAL SERVER ERROR)\n" + message;
    return plainTextResponse(500, message);
  }

  /**
   * Generates an HTTP response with status 500 (INTERNAL SERVER ERROR) and the stack trace of the <b>root cause</b> of the
   * specified exception in the response body.
   * 
   * @param t
   * @return
   */
  public static Response serverError(Throwable t) {
    logger.error(t.getMessage(), t);
    return serverError(getRootCauseStackTrace(t));
  }

  /**
   * Generates an HTTP response with status 500 (INTERNAL SERVER ERROR) and the specified message and the stack trace of the <b>root
   * cause</b> of the specified exception in the response body.
   * 
   * @param t
   * @return
   */
  public static Response serverError(Throwable t, String message) {
    logger.error(message, t);
    message = "500 (INTERNAL SERVER ERROR)\n" + message + "\n" + getRootCauseStackTrace(t);
    return serverError(message);
  }

  /**
   * Generates a 200 (OK) response with the specified message in the response body and a Content-Type header of text/plain.
   * 
   * @param message
   * @return
   */
  public static Response plainTextResponse(String message) {
    return Response.ok(message, MediaType.TEXT_PLAIN).build();
  }

  /**
   * Generates a response with the specified HTTP status code, the specified message in the reponse body and a Content-Type header
   * of text/plain.
   * 
   * @param status
   * @param message
   * @return
   */
  public static Response plainTextResponse(int status, String message) {
    return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * Generate a 200 (OK) response with a Content-Type header of application/xml and the specified XML string in the response body.
   * 
   * @param xml
   * @return
   */
  public static Response xmlResponse(String xml) {
    return Response.ok(xml, MediaType.APPLICATION_XML).build();
  }

  /**
   * Generate a 200 (OK) response with a Content-Type header of application/xml and the specified XML string in the response body.
   * 
   * @param xml
   * @return
   */
  public static Response xmlResponse(StreamingOutput stream) {
    return Response.ok(stream, MediaType.APPLICATION_XML).build();
  }

  /**
   * Generate a 200 (OK) response with a Content-Type header of application/xml and the specified JAXB object converted to XML in
   * the response body.
   * 
   * @param jaxbObject
   * @return
   */
  public static Response xmlResponse(Object jaxbObject) {
    return Response.ok(jaxbObject, MediaType.APPLICATION_XML).build();
  }
  
  public static Response streamingResponse(InputStream is, MediaType mediaType) {
    return Response.ok().type(mediaType).entity(is).build();
  }
  
  public static Response streamingResponse(InputStream is, String mediaType) {
    return Response.ok().type(mediaType).entity(is).build();
  }

  /**
   * Returns a print-out of the specified object.
   * 
   * @param bean
   * @return
   */
  public static String dump(Object bean) {
    StringWriter sw = new StringWriter(4096);
    BeanPrinter bp = new BeanPrinter(new PrintWriter(sw));
    bp.dump(bean);
    return sw.toString();
  }

}
