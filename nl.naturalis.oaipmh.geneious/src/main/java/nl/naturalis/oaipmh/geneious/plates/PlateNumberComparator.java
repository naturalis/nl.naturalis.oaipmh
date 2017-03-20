package nl.naturalis.oaipmh.geneious.plates;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
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

	private static final String MSG0 = "Record with id {} marked for removal. "
			+ "Obsolete document version. Record superseded by record with id {}";

	private static final String MSG1 = "Record with id {} marked for removal. "
			+ "Duplicate {}. Preferring record with id {}";

	public PlateNumberComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{

		DocumentNotes notes0 = ad0.getDocument().getNotes();
		DocumentNotes notes1 = ad1.getDocument().getNotes();

		int i = cmp(ExtractPlateNumberCode_Samples, notes0, notes1);
		if (i != 0) {
			return i;
		}

		String s0 = notes0.get(DocumentVersionCode_Seq);
		String s1 = notes1.get(DocumentVersionCode_Seq);
		int int0 = Integer.parseInt(s0);
		int int1 = Integer.parseInt(s1);
		i = int0 - int1;
		if (i < 0) {
			// Then version0 < version1; remove ad0
			if (!ad0.doNotOutput && logger.isDebugEnabled()) {
				logger.debug(MSG0, ad0.getId(), ad1.getId());
			}
			ad0.doNotOutput = true;
			return i;
		}
		if (i > 0) {
			if (!ad1.doNotOutput && logger.isDebugEnabled()) {
				logger.debug(MSG0, ad1.getId(), ad0.getId());
			}
			ad1.doNotOutput = true;
			return i;
		}

		i = ad0.getId() - ad1.getId();
		if (i < 0) {
			// Then ad1 has a greater database ID than ad0; remove ad1
			if (!ad0.doNotOutput && logger.isDebugEnabled()) {
				logger.debug(MSG1, ad0.getId(), ExtractPlateNumberCode_Samples, ad1.getId());
			}
			ad0.doNotOutput = true;
			return i;
		}
		if (!ad1.doNotOutput && logger.isDebugEnabled()) {
			logger.debug(MSG1, ad1.getId(), ExtractPlateNumberCode_Samples, ad0.getId());
		}
		ad1.doNotOutput = true;
		return i;

	}

	private static int cmp(DocumentNotes.Note note, DocumentNotes notes0, DocumentNotes notes1)
	{
		String s0 = notes0.get(note);
		String s1 = notes1.get(note);
		if (s0 == null) {
			if (s1 == null) {
				return 0;
			}
			// null values last
			return 1;
		}
		if (s1 == null) {
			return -1;
		}
		return s0.compareTo(s1);
	}
}
