package nl.naturalis.oaipmh.geneious.specimens;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;

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
		String regNo0 = ad0.getDocument().getNotes().get(RegistrationNumberCode_Samples);
		String regNo1 = ad1.getDocument().getNotes().get(RegistrationNumberCode_Samples);
		if (regNo0.equals(regNo1)) {
			if (ad0.getId() > ad1.getId()) {
				ad1.doNotOutput = true;
			}
			else {
				ad0.doNotOutput = true;
			}
		}
		return 0;
	}

}
