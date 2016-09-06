package nl.naturalis.oaipmh.geneious.specimens;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, per
 * specimen, selects the instance with the highest database ID.
 * 
 * @see SpecimenSetFilter
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenRegNoComparator implements Comparator<AnnotatedDocument> {

	public SpecimenRegNoComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String regNo0 = ad0.getDocument().getNotes().get(Note.RegistrationNumberCode_Samples);
		String regNo1 = ad0.getDocument().getNotes().get(Note.RegistrationNumberCode_Samples);
		int i = regNo0.compareTo(regNo1);
		return i == 0 ? ad1.getId() - ad0.getId() : i;
	}

}
