package nl.naturalis.oaipmh.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private static String packages = "org.openarchives.oai._2"
			+ ":org.openarchives.oai._2_0.oai_dc" + ":org.purl.dc.elements._1"
			+ ":nl.naturalis.oaipmh.api.jaxb.abcd";

	private JAXBContext jaxbContext;

	public JAXBContextResolver()
	{
		try {
			this.jaxbContext = JAXBContext.newInstance(packages);
		}
		catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public JAXBContext getContext(Class<?> type)
	{
		return jaxbContext;
	}
}
