package nl.naturalis.oaipmh.geneious.specimens;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.BOLDIDCode_Bold;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentPostFilter post-filter} shared by all geneious
 * repositories. Will be applied <i>before</i> any repository-specific
 * post-filters. This filters checks that
 * <ul>
 * <li>The document_xml column contains valid XML
 * <li>The plugin_document_xml columns contains valid XML
 * <li>All required notes are present. The following notes are assumed to be
 * required: CRSCode_CRS, ExtractIDCode_Samples, MarkerCode_Seq,
 * DocumentVersionCode_Seq, RegistrationNumberCode_Samples,
 * ExtractPlateNumberCode_Sample
 * <li>The CRSCode_CRS note has value "true"
 * <li>The is_contig attribute in the plugin_document_xml column has value
 * "true" in case of a DefaultAlignmentDocument
 * </ul>
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenPostFilter implements IAnnotatedDocumentPostFilter {

	private static final Logger logger = LogManager.getLogger(SpecimenPostFilter.class);
	private int numAccepted;
	private int numDiscarded;

	public SpecimenPostFilter()
	{
	}

	@Override
	public boolean accept(AnnotatedDocument ad)
	{
		DocumentNotes notes = ad.getDocument().getNotes();
		boolean ok = checkPresent(RegistrationNumberCode_Samples, notes);
		ok = ok && checkPresent(BOLDIDCode_Bold, notes);
		// ok = ok && checkPresent(BOLDURICode_FixedValue_Bold, notes);
		return ok;
	}

	public int getNumAccepted()
	{
		return numAccepted;
	}

	public int getNumDiscarded()
	{
		return numDiscarded;
	}

	private boolean checkPresent(DocumentNotes.Note note, DocumentNotes notes)
	{
		if (notes.isSet(note) && notes.get(note).trim().length() != 0) {
			return true;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Record discarded. Missing or empty note: <{}>", note);
		}
		++numDiscarded;
		return false;
	}

}
