package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;

import java.util.Comparator;

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
					ad0.doNotOutput = true;
				}
				else if (i0 > i1) {
					ad1.doNotOutput = true;
				}
			}
		}
		return 0;
	}
}
