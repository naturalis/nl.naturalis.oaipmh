package nl.naturalis.oaipmh.geneious.plates;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	private static final Logger logger = LogManager.getLogger(PlateNumberComparator.class);

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Duplicate <{}>: {}. Preferring record with id {}";

	public PlateNumberComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		DocumentNotes.Note note = ExtractPlateNumberCode_Samples;
		String pn0 = ad0.getDocument().getNotes().get(note);
		String pn1 = ad1.getDocument().getNotes().get(note);
		int i = pn0.compareTo(pn1);
		if (i == 0) {
			// Higher database IDs BEFORE lower database IDs:
			i = ad1.getId() - ad0.getId();
			if (i < 0) {
				// Then ad0 has a greater database ID than ad1; remove ad1
				if (!ad1.doNotOutput && logger.isDebugEnabled()) {
					logger.debug(MSG, ad1.getId(), note, pn0, ad0.getId());
				}
				ad1.doNotOutput = true;
			}
			else {
				if (!ad0.doNotOutput && logger.isDebugEnabled()) {
					logger.debug(MSG, ad0.getId(), note, pn0, ad1.getId());
				}
				ad0.doNotOutput = true;
			}
		}
		return i;
	}

}
