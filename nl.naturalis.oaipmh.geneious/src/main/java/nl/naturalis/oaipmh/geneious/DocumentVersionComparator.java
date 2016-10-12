package nl.naturalis.oaipmh.geneious;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, extract ID
 * and marker being equal, selects the instance with the highest
 * {@link DocumentNotes.Note#DocumentVersionCode_Seq document version}.
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
		String ad0ExtractIdCode = ad0.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
		String ad1ExtractIdCode = ad1.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
		int i = ad0ExtractIdCode.compareTo(ad1ExtractIdCode);
		if (i != 0)
			return 0;
		String ad0MarkerCode = ad0.getDocument().getNotes().get(Note.MarkerCode_Seq);
		String ad1MarkerCode = ad1.getDocument().getNotes().get(Note.MarkerCode_Seq);
		i = ad0MarkerCode.compareTo(ad1MarkerCode);
		if (i != 0)
			return 0;
		String ad0DocumentVersion = ad0.getDocument().getNotes().get(Note.DocumentVersionCode_Seq);
		String ad1DocumentVersion = ad1.getDocument().getNotes().get(Note.DocumentVersionCode_Seq);
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

	@SuppressWarnings("static-method")
	public int compare_old(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String ad0ExtractIdCode = ad0.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
		String ad1ExtractIdCode = ad1.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
		int i = ad0ExtractIdCode.compareTo(ad1ExtractIdCode);
		if (i != 0)
			return i;
		String ad0MarkerCode = ad0.getDocument().getNotes().get(Note.MarkerCode_Seq);
		String ad1MarkerCode = ad1.getDocument().getNotes().get(Note.MarkerCode_Seq);
		i = ad0MarkerCode.compareTo(ad1MarkerCode);
		if (i != 0)
			return i;
		String ad0DocumentVersion = ad0.getDocument().getNotes().get(Note.DocumentVersionCode_Seq);
		String ad1DocumentVersion = ad1.getDocument().getNotes().get(Note.DocumentVersionCode_Seq);
		int i0 = Integer.parseInt(ad0DocumentVersion);
		int i1 = Integer.parseInt(ad1DocumentVersion);
		// Document version in descending order
		return i1 - i0;
	}

}
