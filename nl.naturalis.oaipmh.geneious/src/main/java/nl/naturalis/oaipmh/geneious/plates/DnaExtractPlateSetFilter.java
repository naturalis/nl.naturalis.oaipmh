package nl.naturalis.oaipmh.geneious.plates;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentSetFilter set-filter} for DNA extract plates.
 * Ensures that for each extract plate only one record from the
 * annotated_document table is selected. This is, more or less arbitrarily, the
 * record with the highest database ID. In other words, for all records with the
 * same {@link DocumentNotes.Note#ExtractPlateNumberCode_Samples plate number}),
 * only the one with the highest database ID is selected and turned into a
 * &lt;DnaExtractPlate&gt; element in the OAI-PMH output.
 * 
 * @see PlateNumberComparator
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractPlateSetFilter implements IAnnotatedDocumentSetFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractPlateSetFilter.class);

	public DnaExtractPlateSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Applying filter {} AnnotatedDocument instances", input.size());
			String arg0 = PlateNumberComparator.class.getSimpleName();
			logger.debug("Marking duplicates using {}", arg0);
		}
		Collections.sort(input, new PlateNumberComparator());
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			if (ad.doNotOutput) {
				if (logger.isDebugEnabled()) {
					DocumentNotes.Note note = ExtractPlateNumberCode_Samples;
					String plateNo = ad.getDocument().getNotes().get(note);
					logger.debug("Found duplicate {}: {}", note, plateNo);
				}
				continue;
			}
			result.add(ad);
		}
		if (logger.isDebugEnabled()) {
			int i = input.size() - result.size();
			logger.debug("Number of duplicates found and removed: {}", i);
			logger.debug("Number of instances remaining: {}", result.size());
		}
		return result;
	}

}
