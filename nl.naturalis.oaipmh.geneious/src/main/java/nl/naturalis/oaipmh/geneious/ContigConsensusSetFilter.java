package nl.naturalis.oaipmh.geneious;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Set filter shared by all geneious repositories. Filters
 * {@link AnnotatedDocument} records by means of a
 * {@link ContigConsensusComparator}.
 * 
 * @author Ayco Holleman
 *
 */
public class ContigConsensusSetFilter implements IAnnotatedDocumentSetFilter {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(ContigConsensusSetFilter.class);

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		return filterContigIfConsensusPresent(input);
	}

	private static List<AnnotatedDocument> filterContigIfConsensusPresent(
			List<AnnotatedDocument> input)
	{
		Collections.sort(input, new ContigConsensusComparator());
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			if (ad.doNotOutput) {
				continue;
			}
			result.add(ad);
		}
		return result;
	}

}
