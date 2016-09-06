package nl.naturalis.oaipmh.geneious.extracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;

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

	public DnaExtractSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		Collections.sort(input, new ExtractIdComparator());
		String prevID = "";
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			String id = ad.getDocument().getNotes().get(Note.ExtractIDCode_Samples);
			if (id.equals(prevID))
				continue;
			result.add(ad);
			prevID = id;
		}
		return result;
	}

}
