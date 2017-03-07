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

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Duplicate <{}>|<{}>: {}|{}. Preferring record with id {}";

	public ExtractIdComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		if (ad0.doNotOutput && ad1.doNotOutput) {
			return 0;
		}
		String id0 = ad0.getDocument().getNotes().get(ExtractIDCode_Samples);
		String id1 = ad1.getDocument().getNotes().get(ExtractIDCode_Samples);
		if (id0.equals(id1)) {
			String marker0 = ad0.getDocument().getNotes().get(MarkerCode_Seq);
			String marker1 = ad1.getDocument().getNotes().get(MarkerCode_Seq);
			if (marker0.equals(marker1)) {
				if (ad0.getId() > ad1.getId()) {
					if (logger.isDebugEnabled()) {
						DocumentNotes.Note note0 = ExtractIDCode_Samples;
						DocumentNotes.Note note1 = MarkerCode_Seq;
						logger.debug(MSG, ad1.getId(), note0, note1, id0, marker0, ad0.getId());
					}
					ad1.doNotOutput = true;
				}
				else {
					if (logger.isDebugEnabled()) {
						DocumentNotes.Note note0 = ExtractIDCode_Samples;
						DocumentNotes.Note note1 = MarkerCode_Seq;
						logger.debug(MSG, ad0.getId(), note0, note1, id0, marker0, ad1.getId());
					}
					ad0.doNotOutput = true;
				}
			}
		}
		return 0;
	}
}
