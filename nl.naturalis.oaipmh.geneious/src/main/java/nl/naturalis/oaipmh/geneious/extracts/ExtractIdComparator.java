package nl.naturalis.oaipmh.geneious.extracts;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, per DNA
 * extract, selects the instance with the highest database ID.
 * 
 * @see SpecimenSetFilter
 * 
 * @author Ayco Holleman
 *
 */
public class ExtractIdComparator implements Comparator<AnnotatedDocument> {

	public ExtractIdComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String id0 = ad0.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
		String id1 = ad0.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
		int i = id0.compareTo(id1);
		if (i != 0)
			return i;
		String marker0 = ad0.getDocument().getNotes().get(Note.MarkerCode_Seq);
		String marker1 = ad0.getDocument().getNotes().get(Note.MarkerCode_Seq);
		i = marker0.compareTo(marker1);
		if (i != 0)
			return i;
		return ad1.getId() - ad0.getId();
	}
}
