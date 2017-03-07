package nl.naturalis.oaipmh.geneious;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Set filter shared by all geneious repositories. Filters
 * {@link AnnotatedDocument} records by means of a
 * {@link DocumentVersionComparator}. Optionally you can also choose to filter
 * records by means of a {@link ReferenceComparator}. In principle, however,
 * using a {@code ReferenceComparator} is probably useless and unnecessarily
 * expensive since it filters out exactly those records whose reference_count
 * column equals 0, so it's quite a bit easier and cheaper to use that SQL WHERE
 * clause.
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentVersionSetFilter implements IAnnotatedDocumentSetFilter {

	private static final Logger logger = LogManager.getLogger(DocumentVersionSetFilter.class);

	public DocumentVersionSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Applying filter to {} AnnotatedDocument instances", input.size());
			logger.debug("Marking records for removal using {}",
					DocumentVersionComparator.class.getSimpleName());
		}
		Collections.sort(input, new DocumentVersionComparator());
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			if (!ad.doNotOutput) {
				result.add(ad);
			}
		}
		if (logger.isDebugEnabled()) {
			int i = input.size() - result.size();
			logger.debug("Number of duplicates found and removed: {}", i);
			logger.debug("Number of instances remaining: {}", result.size());
		}
		return result;
	}

}
