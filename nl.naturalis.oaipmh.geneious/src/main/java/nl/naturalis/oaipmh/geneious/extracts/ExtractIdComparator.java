package nl.naturalis.oaipmh.geneious.extracts;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.specimens.SpecimenSetFilter;

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
		String s0 = ad0.getDocument().getNotes().get(ExtractIDCode_Samples);
		String s1 = ad1.getDocument().getNotes().get(ExtractIDCode_Samples);
		if (s0.equals(s1)) {
			s0 = ad0.getDocument().getNotes().get(MarkerCode_Seq);
			s1 = ad1.getDocument().getNotes().get(MarkerCode_Seq);
			if (s0.equals(s1)) {
				if (ad0.getId() > ad1.getId()) {
					ad1.doNotOutput = true;
				}
				else {
					ad0.doNotOutput = true;
				}
			}
		}
		return 0;
	}
}
