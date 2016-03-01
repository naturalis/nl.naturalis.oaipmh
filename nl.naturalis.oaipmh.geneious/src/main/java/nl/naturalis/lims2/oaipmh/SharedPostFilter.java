package nl.naturalis.lims2.oaipmh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Filters (eliminates) records from the annotated_document table after they
 * have been converted to {@link AnnotatedDocument} instances by an
 * {@link AnnotatedDocumentFactory}. This class contains fine-grained logic for
 * determining if a record should be part of the OAI-PMH output.
 * 
 * @author Ayco Holleman
 *
 */
public class SharedPostFilter implements IAnnotatedDocumentPostFilter {

	private static final Logger logger = LogManager.getLogger(SharedPostFilter.class);

	public SharedPostFilter()
	{
	}

	@Override
	public boolean accept(AnnotatedDocument ad)
	{
		if (ad.getDocument() == null || ad.getPluginDocument() == null) {
			/*
			 * Caused by invalid XML, but this is already logged by the
			 * AnnotatedDocumentFactory.
			 */
			return false;
		}
		if (ad.getDocument().getNotes() == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: document_xml column contains no usable <note> elements");
			}
			return false;
		}
		if (ad.getPluginDocument() instanceof DefaultAlignmentDocument) {
			DefaultAlignmentDocument dad = (DefaultAlignmentDocument) ad.getPluginDocument();
			if (dad.isContig() == null || dad.isContig() == Boolean.FALSE) {
				if (logger.isDebugEnabled()) {
					logger.debug("Record discarded: <DefaultAlignmentDocument> only considered when is_contig=\"true\"");
				}
				return false;
			}
		}
		return true;
	}

}
