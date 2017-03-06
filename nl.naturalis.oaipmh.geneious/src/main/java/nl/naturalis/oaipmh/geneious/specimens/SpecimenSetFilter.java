package nl.naturalis.oaipmh.geneious.specimens;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentSetFilter set-filter} for Specimens. Ensures that
 * for each specimen only one record from the annotated_document table is
 * selected. This is, more or less arbitrarily, the record with the highest
 * database ID. In other words, for all records with the same
 * {@link DocumentNotes.Note#RegistrationNumberCode_Samples specimen
 * registration number}), only the one with the highest database ID is selected
 * and turned into a &lt;Specimen&gt; element in the OAI-PMH output.
 * 
 * @see SpecimenRegNoComparator
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenSetFilter implements IAnnotatedDocumentSetFilter {

	private static final Logger logger = LogManager.getLogger(SpecimenSetFilter.class);

	public SpecimenSetFilter()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Instantiating {}", getClass().getSimpleName());
		}
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Applying filter to {} AnnotatedDocument instances", input.size());
			String arg0 = SpecimenRegNoComparator.class.getSimpleName();
			logger.debug("Sorting instances using {}", arg0);
		}
		Collections.sort(input, new SpecimenRegNoComparator());
		String prevRegNo = "";
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		DocumentNotes.Note note = RegistrationNumberCode_Samples;
		for (AnnotatedDocument ad : input) {
			String regNo = ad.getDocument().getNotes().get(note);
			if (regNo.equals(prevRegNo)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found duplicate {}: {}", note, regNo);
				}
				continue;
			}
			result.add(ad);
			prevRegNo = regNo;
		}
		if (logger.isDebugEnabled()) {
			int i = input.size() - result.size();
			logger.debug("Number of duplicates found and removed: {}", i);
			logger.debug("Number of instances remaining: {}", result.size());
		}
		return result;
	}
}
