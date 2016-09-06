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
		return i == 0 ? ad1.getId() - ad0.getId() : i;
	}

}
