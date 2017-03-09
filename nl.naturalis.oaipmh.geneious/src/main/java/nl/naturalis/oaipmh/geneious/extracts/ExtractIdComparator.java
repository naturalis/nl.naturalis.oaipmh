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

	private static final Logger logger = LogManager.getLogger(ExtractIdComparator.class);

	private static final String MSG0 = "Record with id {} marked for removal. "
			+ "Duplicate <{}>|<{}>: {}|{}. Preferring record with id {}";

	private static final String MSG1 = "Record with id {} marked for removal. "
			+ "Record with dummy marker code superseded by record with id {} ({})";

	private static final String DUMMY_MARKER = "Dum";

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
			if (marker0.equals(DUMMY_MARKER) && !marker1.equals(DUMMY_MARKER)) {
				if (!ad0.doNotOutput && logger.isDebugEnabled()) {
					msg1(ad0.getId(), ad1.getId(), marker1);
				}
				ad0.doNotOutput = true;
				// Just some large number that stands out when debugging
				i = 8192;
			}
			else if (marker1.equals(DUMMY_MARKER) && !marker0.equals(DUMMY_MARKER)) {
				if (!ad1.doNotOutput && logger.isDebugEnabled()) {
					msg1(ad1.getId(), ad0.getId(), marker0);
				}
				ad1.doNotOutput = true;
				i = -8192;
			}
			else {
				i = marker0.compareTo(marker1);
				if (i == 0) {
					// Higher database IDs BEFORE lower database IDs:
					i = ad1.getId() - ad0.getId();
					if (i < 0) {
						// Then ad0 has a greater database ID than ad1; remove
						// ad1
						if (!ad1.doNotOutput && logger.isDebugEnabled()) {
							msg0(ad1.getId(), note0, note1, id0, marker0, ad0.getId());
						}
						ad1.doNotOutput = true;
					}
					else {
						if (!ad0.doNotOutput && logger.isDebugEnabled()) {
							msg0(ad0.getId(), note0, note1, id0, marker0, ad1.getId());
						}
						ad0.doNotOutput = true;
					}
				}
			}
		}
		return i;
	}

	private static void msg0(Object... args)
	{
		logger.debug(MSG0, args);
	}

	private static void msg1(Object... args)
	{
		logger.debug(MSG1, args);
	}

}
