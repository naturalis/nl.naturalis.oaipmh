package nl.naturalis.oaipmh.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS framework plumbing.
 * 
 * @author Ayco Holleman
 *
 */
@ApplicationPath("/")
public class JAXRS extends Application {

	public JAXRS()
	{
		super();
	}

}
