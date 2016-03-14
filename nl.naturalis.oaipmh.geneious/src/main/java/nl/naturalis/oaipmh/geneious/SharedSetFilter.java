package nl.naturalis.oaipmh.geneious;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

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
public class SharedSetFilter implements IAnnotatedDocumentSetFilter {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(SharedSetFilter.class);

	private boolean useReferenceComparator;

	/**
	 * Whether or not to also apply filtering by means of a
	 * {@link ReferenceComparator}.
	 * 
	 * @return
	 */
	public boolean isUseReferenceComparator()
	{
		return useReferenceComparator;
	}

	/**
	 * Determine whether or not to also apply filtering by means of a
	 * {@link ReferenceComparator}.
	 * 
	 * @param useReferenceComparator
	 */
	public void setUseReferenceComparator(boolean useReferenceComparator)
	{
		this.useReferenceComparator = useReferenceComparator;
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		input = filterOldDocumentVersions(input);
		if (useReferenceComparator) {
			input = filterReferencedDocuments(input);
		}
		return input;
	}

	private static List<AnnotatedDocument> filterOldDocumentVersions(List<AnnotatedDocument> input)
	{
		Collections.sort(input, new DocumentVersionComparator());
		String prevExtractId = "";
		String prevMarker = "";
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			String extractId = ad.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
			String marker = ad.getDocument().getNotes().get(Note.MarkerCode_Seq);
			if (extractId.equals(prevExtractId) && marker.equals(prevMarker))
				continue;
			result.add(ad);
			prevExtractId = extractId;
			prevMarker = marker;
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	private static List<AnnotatedDocument> filterReferencedDocuments(List<AnnotatedDocument> input)
	{
		ReferenceComparator comparator = new ReferenceComparator();
		Collections.sort(input, comparator);
		int discarded = comparator.countDispensableRecords();
		return input.subList(0, input.size() - discarded);
	}

}
