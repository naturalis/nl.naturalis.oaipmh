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

	private static final Logger logger = LogManager.getLogger(ContigConsensusSetFilter.class);

	public ContigConsensusSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Applying filter to {} AnnotatedDocument instances", input.size());
			logger.debug("Marking records for removal using {}",
					ContigConsensusComparator.class.getSimpleName());
		}
		Collections.sort(input, new ContigConsensusComparator());
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
