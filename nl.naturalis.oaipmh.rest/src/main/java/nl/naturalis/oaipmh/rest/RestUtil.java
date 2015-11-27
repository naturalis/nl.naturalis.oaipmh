package nl.naturalis.oaipmh.rest;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.domainobject.util.debug.BeanPrinter;

public class RestUtil {

	private RestUtil()
	{
	}

	/**
	 * Generates an HTTP response with status 500 (INTERNAL SERVER ERROR) and the
	 * specified message in the response body. The content type of the response body is
	 * set to text/plain.
	 * 
	 * @param message
	 * @return
	 */
	public static Response serverError(String message)
	{
		message = "500 (INTERNAL SERVER ERROR)\n" + message;
		return plainTextResponse(500, message);
	}

	/**
	 * Generates an HTTP response with status 500 (INTERNAL SERVER ERROR) and the stack
	 * trace of the <b>root cause</b> of the specified exception in the response body.
	 * 
	 * @param t
	 * @return
	 */
	public static Response serverError(Throwable t)
	{
		return serverError(getStackTraceOfRootCause(t));
	}

	/**
	 * Generates an HTTP response with status 500 (INTERNAL SERVER ERROR) and the
	 * specified message and the stack trace of the <b>root cause</b> of the specified
	 * exception in the response body.
	 * 
	 * @param t
	 * @return
	 */
	public static Response serverError(Throwable t, String message)
	{
		message = "500 (INTERNAL SERVER ERROR)\n" + message + "\n" + getStackTraceOfRootCause(t);
		return serverError(message);
	}

	/**
	 * Generates a 200 (OK) response with the specified message in the response body and a
	 * Content-Type header of text/plain.
	 * 
	 * @param message
	 * @return
	 */
	public static Response plainTextResponse(String message)
	{
		return Response.ok(message, MediaType.TEXT_PLAIN).build();
	}

	/**
	 * Generates a response with the specified HTTP status code, the specified message in
	 * the reponse body and a Content-Type header of text/plain.
	 * 
	 * @param status
	 * @param message
	 * @return
	 */
	public static Response plainTextResponse(int status, String message)
	{
		return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
	}

	/**
	 * Generate a 200 (OK) response with a Content-Type header of application/xml and the
	 * specified JAXB object converted to XML in the response body.
	 * 
	 * @param jaxbObject
	 * @return
	 */
	public static Response jaxbResponse(Object jaxbObject)
	{
		return Response.ok(jaxbObject, MediaType.APPLICATION_XML).build();
	}

	/**
	 * Returns a print-out of the specified object.
	 * 
	 * @param bean
	 * @return
	 */
	public static String dump(Object bean)
	{
		StringWriter sw = new StringWriter(4096);
		BeanPrinter bp = new BeanPrinter(new PrintWriter(sw));
		bp.dump(bean);
		return sw.toString();
	}

	private static String getStackTraceOfRootCause(Throwable t)
	{
		while (t.getCause() != null)
			t = t.getCause();
		StringBuilder sb = new StringBuilder(6000);
		sb.append(t.toString());
		for (StackTraceElement e : t.getStackTrace()) {
			sb.append("\nat ");
			sb.append(e.getClassName()).append('.').append(e.getMethodName());
			sb.append('(').append(e.getFileName());
			sb.append(':').append(e.getLineNumber()).append(')');
		}
		return sb.toString();
	}

}
