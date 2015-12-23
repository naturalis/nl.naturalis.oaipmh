package nl.naturalis.oaipmh.api.util;

import static javax.xml.bind.Marshaller.JAXB_SCHEMA_LOCATION;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.naturalis.oaipmh.api.IOAIRepository;

import org.domainobject.util.CollectionUtil;
import org.openarchives.oai._2.OAIPMHtype;

public class OAIPMHMarshaller {

	private static final List<String> CORE_PACKAGES = Arrays.asList("org.openarchives.oai._2",
			"org.openarchives.oai._2_0.oai_dc", "org.purl.dc.elements._1");

	private static final String OAI_NAMESPACE = "http://www.openarchives.org/OAI/2.0/";
	private static final String OAI_SCHEMA = "http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd";
	private static final String OAI_DC_NAMESPACE = "http://www.openarchives.org/OAI/2.0/oai_dc/";
	private static final String OAI_DC_SCHEMA = "http://www.openarchives.org/OAI/2.0/oai_dc.xsd";

	private final Set<String> pkgs;
	private final Map<String, String> schemas;

	private OAIPMHtype root;

	public OAIPMHMarshaller()
	{
		pkgs = new HashSet<>();
		pkgs.addAll(CORE_PACKAGES);
		schemas = new HashMap<>();
		schemas.put(OAI_NAMESPACE, OAI_SCHEMA);
	}

	/**
	 * Set the JAXB root object for OAI-PMH marshallling.
	 * 
	 * @param root
	 */
	public void setRootElement(OAIPMHtype root)
	{
		this.root = root;
	}

	/**
	 * Add a package containing JAXB classes. The following packages are
	 * automatical ly added:
	 * <ol>
	 * <li>org.openarchives.oai._2 (OAI-PMH schema)
	 * <li>org.openarchives.oai._2_0.oai_dc (oai_dc container)
	 * <li>org.purl.dc.elements._1 (DarwinCore)
	 * </ol>
	 * If you implement you {@link IOAIRepository} using JAXB, you must add any
	 * extra packages (most likely generated by xjc) used to generate the final
	 * OAI-PMH output.
	 * 
	 * @param pkg
	 */
	public void addJaxbPackage(String pkg)
	{
		pkgs.add(pkg);
	}

	/**
	 * Set the location of an XML schema definition. The location of the XSD for
	 * OAI-PMH is automatically added. The XSD location for oai_dc, however, is
	 * not automatically added since you might be serving a request for a
	 * different metadata format. However, you can simply add the XSD location
	 * for oai_dc by calling {@link #addOaiDcSchemaLocation()}.
	 * 
	 * @param xmlNamespace
	 * @param schemaLocation
	 */
	public void addSchemaLocation(String xmlNamespace, String schemaLocation)
	{
		schemas.put(xmlNamespace, schemaLocation);
	}

	/**
	 * Add the XSD location of the oai_dc namespace.
	 */
	public void addOaiDcSchemaLocation()
	{
		schemas.put(OAI_DC_NAMESPACE, OAI_DC_SCHEMA);
	}

	/**
	 * Write the OAI-PMH to the specified output stream.
	 * 
	 * @return
	 * @throws JAXBException
	 */
	public void marshal(OutputStream out) throws JAXBException
	{
		JAXBContext ctx = JAXBContext.newInstance(CollectionUtil.implode(pkgs, ":"));
		Marshaller marshaller = ctx.createMarshaller();
		StringBuilder xsds = new StringBuilder(200);
		boolean first = true;
		for (String ns : schemas.keySet()) {
			if (first)
				first = false;
			else
				xsds.append(' ');
			xsds.append(ns).append(' ');
			xsds.append(schemas.get(ns));
		}
		marshaller.setProperty(JAXB_SCHEMA_LOCATION, xsds.toString());
		marshaller.marshal(root, out);
	}

}
