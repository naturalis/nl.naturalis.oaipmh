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

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String ad0ExtractIdCode = ad0.getDocument().getNote(ExtractIDCode_Samples);
		String ad1ExtractIdCode = ad1.getDocument().getNote(ExtractIDCode_Samples);
		if (!ad0ExtractIdCode.equals(ad1ExtractIdCode)) {
			return 0;
		}
		String ad0MarkerCode = ad0.getDocument().getNote(MarkerCode_Seq);
		String ad1MarkerCode = ad1.getDocument().getNote(MarkerCode_Seq);
		if (!ad0MarkerCode.equals(ad1MarkerCode)) {
			return 0;
		}
		String ad0DocumentVersion = ad0.getDocument().getNote(DocumentVersionCode_Seq);
		String ad1DocumentVersion = ad1.getDocument().getNote(DocumentVersionCode_Seq);
		int i0 = Integer.parseInt(ad0DocumentVersion);
		int i1 = Integer.parseInt(ad1DocumentVersion);
		if (i0 < i1) {
			ad0.doNotOutput = true;
		}
		else if (i0 > i1) {
			ad1.doNotOutput = true;
		}
		return 0;
	}

}
