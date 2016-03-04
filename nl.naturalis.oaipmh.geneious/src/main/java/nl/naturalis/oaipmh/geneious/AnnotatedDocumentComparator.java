package nl.naturalis.oaipmh.geneious;

import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Compares {@link AnnotatedDocument} instances based on whether one references
 * the other, or whether one is input for the other. If one
 * {@code AnnotatedDocument} references another {@code AnnotatedDocument}, it
 * means it has superseded the other {@code AnnotatedDocument}. Thus when
 * sorting {@link AnnotatedDocument} instances, those that reference others
 * should be pushed up, while those that <i>are</i> referenced should be pushed
 * down.
 * 
 * @author Ayco Holleman
 *
 */
public class AnnotatedDocumentComparator implements Comparator<AnnotatedDocument> {

	private static final Logger logger = LogManager.getLogger(AnnotatedDocumentComparator.class);

	private static final String MSG_DISCARD = "Record [id={}] is discarded in favour of "
			+ "record [id={}]. Reason: {}";

	private static final String REFERENCE_CHECK = "it is referenced by the latter";
	private static final String INPUT_CHECK = "it is input for the latter";
	private static final String OUTPUT_CHECK = "the latter was output from it";

	public AnnotatedDocumentComparator()
	{
	}

	private int numDispensable;

	/**
	 * Compares the specified {@link AnnotatedDocument} instances. A side-effect
	 * of the comparison is that the referenced document will be marked as
	 * dispensable. That is, it will be excluded from the OAI-PMH output.
	 */
	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		int i = checkReferences(ad0, ad1);
		if (i != 0)
			return i;
		i = checkInputDocuments(ad0, ad1);
		if (i != 0)
			return i;
		return checkOutputDocuments(ad0, ad1);
	}

	private int checkReferences(AnnotatedDocument first, AnnotatedDocument second)
	{
		// Does the 1st document reference the 2nd? Then we can discard the 2nd.
		List<String> urns = first.getDocument().getReferencedDocuments();
		if (urns != null && urns.contains(second.getUrn())) {
			dispense(second, first, REFERENCE_CHECK);
			return -1;
		}
		// Does the 2nd document reference the 1st? Then we can discard the 1st.
		urns = second.getDocument().getReferencedDocuments();
		if (urns != null && urns.contains(first.getUrn())) {
			dispense(first, second, REFERENCE_CHECK);
			return 1;
		}
		return 0;
	}

	public int checkInputDocuments(AnnotatedDocument first, AnnotatedDocument second)
	{
		// Is the 2nd document an input document for 1st? Then we can discard
		// the 2nd.
		if (first.getPluginDocument() instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement x = (XMLSerialisableRootElement) first.getPluginDocument();
			List<String> inputForFirst = x.getInputDocuments();
			if (inputForFirst != null && inputForFirst.contains(second.getUrn())) {
				dispense(second, first, INPUT_CHECK);
				return -1;
			}
		}
		// Is the 1st document an input document for 2nd? Then we can discard
		// the 1st.
		if (second.getPluginDocument() instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement x = (XMLSerialisableRootElement) second.getPluginDocument();
			List<String> inputForSecond = x.getInputDocuments();
			if (inputForSecond != null && inputForSecond.contains(first.getUrn())) {
				dispense(first, second, INPUT_CHECK);
				return 1;
			}
		}
		return 0;
	}

	public int checkOutputDocuments(AnnotatedDocument first, AnnotatedDocument second)
	{
		// Is the 1st document generated from the 2nd? The we can discard the
		// 2nd.
		if (second.getPluginDocument() instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement x = (XMLSerialisableRootElement) second.getPluginDocument();
			String outputOfSecond = x.getOutputDocument();
			if (outputOfSecond != null && outputOfSecond.equals(first.getUrn())) {
				dispense(second, first, OUTPUT_CHECK);
			}
			return -1;
		}
		// Is the 2nd document generated from the 1st? The we can discard the
		// 1st.
		if (first.getPluginDocument() instanceof XMLSerialisableRootElement) {
			XMLSerialisableRootElement x = (XMLSerialisableRootElement) first.getPluginDocument();
			String outputOfFirst = x.getOutputDocument();
			if (outputOfFirst != null && outputOfFirst.equals(second.getUrn())) {
				dispense(first, second, OUTPUT_CHECK);
			}
			return 1;
		}
		return 0;
	}

	public int countDispensableRecords()
	{
		return numDispensable;
	}

	private void dispense(AnnotatedDocument dispensable, AnnotatedDocument nonDispensable,
			String reason)
	{
		if (!dispensable.dispensable) {
			if (logger.isDebugEnabled()) {
				logger.debug(MSG_DISCARD, dispensable.getId(), nonDispensable.getId(), reason);
			}
			++numDispensable;
			dispensable.dispensable = true;
		}
	}

}
