package nl.naturalis.oaipmh.geneious.plates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;

/**
 * A {@link IAnnotatedDocumentSetFilter set-filter} for DNA extract plates.
 * Ensures that for each extract plate only one record is selected. This is,
 * more or less arbitrarily, the record with the highest database ID. In other
 * words, for all recprds with the same
 * {@link DocumentNotes.Note#ExtractPlateNumberCode_Samples plate number}), the
 * one with the highest database ID is selected.
 * 
 * @see PlateNumberComparator
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractPlateSetFilter implements IAnnotatedDocumentSetFilter {

	public DnaExtractPlateSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		Collections.sort(input, new PlateNumberComparator());
		String prevPlateNo = "";
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			String plateNo = ad.getDocument().getNotes().get(Note.ExtractPlateNumberCode_Samples);
			if (plateNo.equals(prevPlateNo))
				continue;
			result.add(ad);
			prevPlateNo = plateNo;
		}
		return result;
	}

}
