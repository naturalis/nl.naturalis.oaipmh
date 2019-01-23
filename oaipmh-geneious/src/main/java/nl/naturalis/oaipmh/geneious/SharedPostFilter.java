package nl.naturalis.oaipmh.geneious;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.CRSCode_CRS;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.PlatePositionCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;

/**
 * A {@link IAnnotatedDocumentPostFilter post-filter} shared by all {@link GeneiousOAIRepository geneious repositories}. Will be
 * applied <i>before</i> any repository-specific post-filters. This filters checks that
 * <ul>
 * <li>The document_xml column contains valid XML
 * <li>The plugin_document_xml columns contains valid XML
 * <li>All required notes are present. The following notes are assumed to be required: CRSCode_CRS, ExtractIDCode_Samples,
 * MarkerCode_Seq, DocumentVersionCode_Seq, RegistrationNumberCode_Samples, ExtractPlateNumberCode_Sample
 * <li>The CRSCode_CRS note has value "true"
 * <li>The is_contig attribute in the plugin_document_xml column has value "true" in case of a DefaultAlignmentDocument
 * </ul>
 * 
 * @author Ayco Holleman
 *
 */
public class SharedPostFilter implements IAnnotatedDocumentPostFilter {

  private static final Logger logger = LoggerFactory.getLogger(SharedPostFilter.class);

  private static final Note[] requiredNotes = new Note[] {CRSCode_CRS, ExtractIDCode_Samples,
      MarkerCode_Seq, DocumentVersionCode_Seq, RegistrationNumberCode_Samples,
      ExtractPlateNumberCode_Samples, PlatePositionCode_Samples};

  private int numAccepted;
  private int numDiscarded;

  public SharedPostFilter() {}

  @Override
  public boolean accept(AnnotatedDocument ad) {
    if (ad.getDocument() == null || ad.getPluginDocument() == null) {
      /*
       * Caused by invalid XML, but this is already logged by the AnnotatedDocumentFactory.
       */
      return false;
    }
    DocumentNotes notes;
    if ((notes = ad.getDocument().getNotes()) == null) {
      if (logger.isDebugEnabled()) {
        discard("No usable <note> elements in document_xml");
      }
      ++numDiscarded;
      return false;
    }
    for (Note requiredNote : requiredNotes) {
      if (notes.get(requiredNote) == null) {
        if (logger.isDebugEnabled()) {
          discard("Missing required element: <{}>", requiredNote);
        }
        ++numDiscarded;
        return false;
      }
    }
    if (!notes.get(CRSCode_CRS).equals("true")) {
      if (logger.isDebugEnabled()) {
        discard("CRSCode_CRS flag must be set to \"true\"");
      }
      ++numDiscarded;
      return false;
    }
    if (ad.getPluginDocument() instanceof DefaultAlignmentDocument) {
      DefaultAlignmentDocument dad = (DefaultAlignmentDocument) ad.getPluginDocument();
      if (dad.isContig() == null || dad.isContig() == Boolean.FALSE) {
        if (logger.isDebugEnabled()) {
          discard("<DefaultAlignmentDocument> only considered when is_contig=\"true\"");
        }
        ++numDiscarded;
        return false;
      }
    }
    ++numAccepted;
    return true;
  }

  public int getNumAccepted() {
    return numAccepted;
  }

  public int getNumDiscarded() {
    return numDiscarded;
  }

  private static void discard(String msg, Object... args) {
    logger.debug("Record discarded. " + msg, args);
  }

}
