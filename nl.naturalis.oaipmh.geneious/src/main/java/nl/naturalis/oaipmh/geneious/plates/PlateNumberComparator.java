package nl.naturalis.oaipmh.geneious.plates;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;

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
		String pn0 = ad0.getDocument().getNotes().get(ExtractPlateNumberCode_Samples);
		String pn1 = ad1.getDocument().getNotes().get(ExtractPlateNumberCode_Samples);
		if (pn0.equals(pn1)) {
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
