package nl.naturalis.lims2.oaipmh;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Removes annotated_document records from the set of annotated_document records
 * retrieved from the database by using an {@link AnnotatedDocumentComparator}.
 * 
 * @author Ayco Holleman
 *
 */
public class SharedSetFilter implements IAnnotatedDocumentSetFilter {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(AnnotatedDocumentComparator.class);

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		AnnotatedDocumentComparator comparator = new AnnotatedDocumentComparator();
		Collections.sort(input, comparator);
		int discarded = comparator.countDispensableRecords();
		return input.subList(0, input.size() - discarded);
	}

}
