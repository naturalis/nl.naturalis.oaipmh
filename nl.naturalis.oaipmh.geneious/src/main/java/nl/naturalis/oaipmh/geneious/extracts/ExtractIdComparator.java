package nl.naturalis.oaipmh.geneious.extracts;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.specimens.SpecimenSetFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	public static void main(String[] args)
	{
		String s0 = "Ayco Holleman";
		String s1 = "Ayco Hollemam ";
		System.out.println("length s0: " + s0.length());
		System.out.println("length s1: " + s1.length());
		System.out.println("diff: " + (s0.length() - s1.length()));
		System.out.println("comp: " + s1.compareTo(s0));
	}

	private static final Logger logger = LogManager.getLogger(ExtractIdComparator.class);

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Duplicate <{}>|<{}>: {}|{}. Preferring record with id {}";

	public ExtractIdComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		DocumentNotes.Note note0 = ExtractIDCode_Samples;
		DocumentNotes.Note note1 = MarkerCode_Seq;
		String id0 = ad0.getDocument().getNotes().get(ExtractIDCode_Samples);
		String id1 = ad1.getDocument().getNotes().get(ExtractIDCode_Samples);
		int i = id0.compareTo(id1);
		if (i == 0) {
			String marker0 = ad0.getDocument().getNotes().get(MarkerCode_Seq);
			String marker1 = ad1.getDocument().getNotes().get(MarkerCode_Seq);
			i = marker0.compareTo(marker1);
			if (i == 0) {
				// Higher database IDs BEFORE lower database IDs:
				i = ad1.getId() - ad0.getId();
				if (i < 0) {
					// Then ad0 has a greater database ID than ad1; remove ad1
					if (!ad1.doNotOutput && logger.isDebugEnabled()) {
						logger.debug(MSG, ad1.getId(), note0, note1, id0, marker0, ad0.getId());
					}
					ad1.doNotOutput = true;
				}
				else {
					if (!ad0.doNotOutput && logger.isDebugEnabled()) {
						logger.debug(MSG, ad0.getId(), note0, note1, id0, marker0, ad1.getId());
					}
					ad0.doNotOutput = true;
				}
			}
		}
		return i;
	}
}
