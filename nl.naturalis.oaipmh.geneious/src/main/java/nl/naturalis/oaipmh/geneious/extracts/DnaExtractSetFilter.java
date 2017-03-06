package nl.naturalis.oaipmh.geneious.extracts;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link IAnnotatedDocumentSetFilter set-filter} for DNA extracts. Ensures
 * that for each DNA extract only one record from the annotated_document table
 * is selected. This is, more or less arbitrarily, the record with the highest
 * database ID. In other words, for all records with the same
 * {@link DocumentNotes.Note#ExtractIDCode_Samples extract ID}), only the one
 * with the highest database ID is selected and turned into a &lt;DnaExtract&gt;
 * element in the OAI-PMH output.
 * 
 * @see ExtractIdComparator
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractSetFilter implements IAnnotatedDocumentSetFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractSetFilter.class);

	public DnaExtractSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Applying filter to {} AnnotatedDocument instances", input.size());
			String arg0 = ExtractIdComparator.class.getSimpleName();
			logger.debug("Sorting instances using {}", arg0);
		}
		Collections.sort(input, new ExtractIdComparator());
		String prevID = "";
		String prevMarker = "";
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			String id = ad.getDocument().getNotes().get(ExtractIDCode_Samples);
			String marker = ad.getDocument().getNotes().get(MarkerCode_Seq);
			if (id.equals(prevID) && marker.equals(prevMarker)) {
				if (logger.isDebugEnabled()) {
					String fmt = "Found duplicate {}|{}: {}|{}";
					logger.debug(fmt, ExtractIDCode_Samples, MarkerCode_Seq, id, marker);
				}
				continue;
			}
			result.add(ad);
			prevID = id;
			prevMarker = marker;
		}
		if (logger.isDebugEnabled()) {
			int i = input.size() - result.size();
			logger.debug("Number of duplicates found and removed: {}", i);
			logger.debug("Number of instances remaining: {}", result.size());
		}
		return result;
	}

}
