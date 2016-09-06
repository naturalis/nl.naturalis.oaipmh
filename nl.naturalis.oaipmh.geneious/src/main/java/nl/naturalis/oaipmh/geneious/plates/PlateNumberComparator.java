package nl.naturalis.oaipmh.geneious.plates;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, per plate
 * number, selects the instance with the highest database ID.
 * 
 * @see DnaExtractPlateSetFilter
 * 
 * @author Ayco Holleman
 *
 */
public class PlateNumberComparator implements Comparator<AnnotatedDocument> {

	public PlateNumberComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String pn0 = ad0.getDocument().getNotes().get(Note.ExtractPlateNumberCode_Samples);
		String pn1 = ad0.getDocument().getNotes().get(Note.ExtractPlateNumberCode_Samples);
		int i = pn0.compareTo(pn1);
		return i == 0 ? ad1.getId() - ad0.getId() : i;
	}

}
