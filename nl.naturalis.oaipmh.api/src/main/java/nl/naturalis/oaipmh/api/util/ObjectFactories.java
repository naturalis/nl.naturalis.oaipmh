package nl.naturalis.oaipmh.api.util;

/**
 * Provides JAXB object factories.
 * 
 * @author Ayco Holleman
 *
 */
public class ObjectFactories {

	public static final org.openarchives.oai._2.ObjectFactory oaiFactory;
	public static final org.openarchives.oai._2_0.oai_dc.ObjectFactory oaiDcFactory;
	public static final org.purl.dc.elements._1.ObjectFactory dcFactory;
	public static final nl.naturalis.oaipmh.api.jaxb.abcd.ObjectFactory abcdFactory;
	public static final nl.naturalis.oaipmh.api.jaxb.ggbn.ObjectFactory ggbnFactory;

	static {
		oaiFactory = new org.openarchives.oai._2.ObjectFactory();
		oaiDcFactory = new org.openarchives.oai._2_0.oai_dc.ObjectFactory();
		dcFactory = new org.purl.dc.elements._1.ObjectFactory();
		abcdFactory = new nl.naturalis.oaipmh.api.jaxb.abcd.ObjectFactory();
		ggbnFactory = new nl.naturalis.oaipmh.api.jaxb.ggbn.ObjectFactory();
	}

	private ObjectFactories()
	{
	}

}
