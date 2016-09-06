package nl.naturalis.oaipmh.geneious.specimens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;

/**
 * A {@link IAnnotatedDocumentSetFilter set-filter} for Specimens. Ensures that
 * for each specimen only one record from the annotated_document table is
 * selected. This is, more or less arbitrarily, the record with the highest
 * database ID. In other words, for all records with the same
 * {@link DocumentNotes.Note#RegistrationNumberCode_Samples specimen
 * registration number}), only the one with the highest database ID is selected
 * and turned into a &lt;DnaExtractPlate&gt; element in the OAI-PMH output.
 * 
 * @see SpecimenRegNoComparator
 * 
 * @author Ayco Holleman
 *
 */
public class SpecimenSetFilter implements IAnnotatedDocumentSetFilter {

	public SpecimenSetFilter()
	{
	}

	@Override
	public List<AnnotatedDocument> filter(List<AnnotatedDocument> input)
	{
		Collections.sort(input, new SpecimenRegNoComparator());
		String prevRegNo = "";
		List<AnnotatedDocument> result = new ArrayList<>(input.size());
		for (AnnotatedDocument ad : input) {
			String regNo = ad.getDocument().getNotes().get(Note.RegistrationNumberCode_Samples);
			if (regNo.equals(prevRegNo))
				continue;
			result.add(ad);
			prevRegNo = regNo;
		}
		return result;
	}

}
