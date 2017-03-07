package nl.naturalis.oaipmh.geneious.specimens;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;

import java.util.Comparator;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, per
 * specimen, selects the instance with the highest database ID.
 * 
 * @see SpecimenSetFilter
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenUnitIDComparator implements Comparator<AnnotatedDocument> {

	private static final Logger logger = LogManager.getLogger(SpecimenUnitIDComparator.class);

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Duplicate <{}>: {}. Preferring record with id {}";

	public SpecimenUnitIDComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		if (ad0.doNotOutput && ad1.doNotOutput) {
			return 0;
		}
		String regNo0 = ad0.getDocument().getNotes().get(RegistrationNumberCode_Samples);
		String regNo1 = ad1.getDocument().getNotes().get(RegistrationNumberCode_Samples);
		if (regNo0.equals(regNo1)) {
			if (ad0.getId() > ad1.getId()) {
				if (logger.isDebugEnabled()) {
					DocumentNotes.Note note0 = RegistrationNumberCode_Samples;
					logger.debug(MSG, ad1.getId(), note0, regNo0, ad0.getId());
				}
				ad1.doNotOutput = true;
			}
			else {
				if (logger.isDebugEnabled()) {
					DocumentNotes.Note note0 = RegistrationNumberCode_Samples;
					logger.debug(MSG, ad0.getId(), note0, regNo0, ad1.getId());
				}
				ad0.doNotOutput = true;
			}
		}
		return 0;
	}

}
