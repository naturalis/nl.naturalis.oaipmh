package nl.naturalis.oaipmh.geneious.plates;

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
			logger.debug("Marking records for removal using {}",
					PlateNumberComparator.class.getSimpleName());
		}
		Collections.sort(input, new PlateNumberComparator());
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			if (!ad.doNotOutput) {
				result.add(ad);
			}
		}
		if (logger.isDebugEnabled()) {
			int i = input.size() - result.size();
			logger.debug("Number of removed instances: {}", i);
			logger.debug("Number of instances remaining: {}", result.size());
		}
		return result;
	}

}
