package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, extract ID
 * and marker being equal, selects the instance with the highest
 * {@link DocumentNotes.Note#DocumentVersionCode_Seq document version}.
 * 
 * @see ContigConsensusComparator
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentVersionComparator implements Comparator<AnnotatedDocument> {

	private static final Logger logger = LogManager.getLogger(DocumentVersionComparator.class);

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Obsolete document version ({}). Record superseded by record with id {}";

	public DocumentVersionComparator()
	{
	}

	/*
	 * This is a "fake" Comparator, only used to set the
	 * AnnotatedDocument.doNotOutput field when Collections.sort() is called.
	 * The sort order won't be affected when using this comparator, since this
	 * method always returns 0.
	 */
	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		if (ad0.doNotOutput && ad1.doNotOutput) {
			return 0;
		}
		String s0 = ad0.getDocument().getNote(ExtractIDCode_Samples);
		String s1 = ad1.getDocument().getNote(ExtractIDCode_Samples);
		if (s0.equals(s1)) {
			s0 = ad0.getDocument().getNote(MarkerCode_Seq);
			s1 = ad1.getDocument().getNote(MarkerCode_Seq);
			if (s0.equals(s1)) {
				s0 = ad0.getDocument().getNote(DocumentVersionCode_Seq);
				s1 = ad1.getDocument().getNote(DocumentVersionCode_Seq);
				int i0 = Integer.parseInt(s0);
				int i1 = Integer.parseInt(s1);
				if (i0 < i1) {
					if (logger.isDebugEnabled()) {
						logger.debug(MSG, ad0.getId(), i0, ad1.getId());
					}
					ad0.doNotOutput = true;
				}
				else if (i0 > i1) {
					if (logger.isDebugEnabled()) {
						logger.debug(MSG, ad1.getId(), i1, ad0.getId());
					}
					ad1.doNotOutput = true;
				}
			}
		}
		return 0;
	}
}
