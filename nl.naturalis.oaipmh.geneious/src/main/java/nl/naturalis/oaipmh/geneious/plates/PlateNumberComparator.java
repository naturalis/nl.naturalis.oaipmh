package nl.naturalis.oaipmh.geneious.plates;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;

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
		if (ad0.doNotOutput && ad1.doNotOutput) {
			return 0;
		}
		String pn0 = ad0.getDocument().getNotes().get(ExtractPlateNumberCode_Samples);
		String pn1 = ad1.getDocument().getNotes().get(ExtractPlateNumberCode_Samples);
		if (pn0.equals(pn1)) {
			if (ad0.getId() > ad1.getId()) {
				if (logger.isDebugEnabled()) {
					logger.debug(MSG, ad1.getId(), ExtractPlateNumberCode_Samples, pn0, ad0.getId());
				}
				ad1.doNotOutput = true;
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug(MSG, ad0.getId(), ExtractPlateNumberCode_Samples, pn0, ad1.getId());
				}
				ad0.doNotOutput = true;
			}
		}
		return 0;
	}

}
