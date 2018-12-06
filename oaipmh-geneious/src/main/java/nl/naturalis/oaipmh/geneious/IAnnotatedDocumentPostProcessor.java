package nl.naturalis.oaipmh.geneious;

import org.openarchives.oai._2.RecordType;

/**
 * A {@code IAnnotatedDocumentPostProcessor} allows for the manipulation of data
 * just before it is converted to XML. Post processors are applied after all
 * filters have been applied and after the remaining {@link AnnotatedDocument}
 * instances have been converted to JAXB objects representing the &lt;record&gt;
 * elements underneath the &lt;OAI-PMH&gt; root element. The post processor
 * allows you to manipulate these JAXB objects just before they are serialized
 * to XML.
 * 
 * @author Ayco Holleman
 *
 */
public interface IAnnotatedDocumentPostProcessor {

	/**
	 * Allows for the manipulation of data within OAI-PMH's &lt;record&gt;
	 * element.
	 * 
	 * @param record
	 *            The JAXB object representing the &lt;record&gt; element
	 * @param annotatedDocument
	 *            The {@code AnnotatedDocument} from which the JAXB object was
	 *            created.
	 * @throws PostProcessingException
	 */
	void process(RecordType record, AnnotatedDocument annotatedDocument)
			throws PostProcessingException;

}
