package nl.naturalis.oaipmh.api;

public class ObjectFactories {

	public static final org.openarchives.oai._2.ObjectFactory oaiFactory;
	public static final org.openarchives.oai._2_0.oai_dc.ObjectFactory oaiDcFactory;
	public static final org.purl.dc.elements._1.ObjectFactory dcFactory;

	static {
		oaiFactory = new org.openarchives.oai._2.ObjectFactory();
		oaiDcFactory = new org.openarchives.oai._2_0.oai_dc.ObjectFactory();
		dcFactory = new org.purl.dc.elements._1.ObjectFactory();
	}

	private ObjectFactories()
	{
	}

}
