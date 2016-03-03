package nl.naturalis.lims2.oaipmh;

import nl.naturalis.lims2.oaipmh.DocumentNotes.Note;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Post-filter shared by all geneious repositories. Will be applied
 * <i>before</i> any repository-specific post-filters. This filters checks that
 * <ul>
 * <li>The document_xml column contains valid XML
 * <li>The plugin_document_xml columns contains valid XML
 * <li>The document_xml column contains at least one &lt;note&gt; element
 * <li>The CRSCode_CRS note is present and has value "true"
 * <li>The is_contig attribute in the plugin_document_xml column has value true
 * in case of a DefaultAlignmentDocument
 * </ul>
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
		DocumentNotes notes;
		if ((notes = ad.getDocument().getNotes()) == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: document_xml column contains no usable <note> elements");
			}
			return false;
		}
		String s = notes.get(Note.CRSCode_CRS);
		if (s == null || !s.equals("true")) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: CRSCode_CRS flag absent or not \"true\"");
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
