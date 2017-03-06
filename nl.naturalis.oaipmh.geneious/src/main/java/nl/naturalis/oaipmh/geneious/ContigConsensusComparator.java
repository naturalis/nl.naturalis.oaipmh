package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.description;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isConsensus;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isContig;

import java.util.Comparator;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances according to
 * whether one can be said to represent a consensus sequence created from the
 * contig sequence represented by the other.
 * 
 * @author Ayco Holleman
 *
 */
public class ContigConsensusComparator implements Comparator<AnnotatedDocument> {

	public ContigConsensusComparator()
	{
	}

	/*
	 * This is a "fake" Comparator, only used to set the
	 * AnnotatedDocument.doNotOutput field when Collections.sort() is called.
	 * The sort order won't be affected when using this comparator, since this
	 * method always returns 0.
	 */
	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String descr0 = ad0.getDocument().getHiddenField(description);
		if (descr0 == null) {
			return 0;
		}
		String descr1 = ad1.getDocument().getHiddenField(description);
		if (descr1 == null) {
			return 0;
		}
		if (!descr0.equals(descr1)) {
			return 0;
		}
		if (isContig(ad0) && isConsensus(ad1)) {
			ad0.doNotOutput = true;
		}
		else if (isContig(ad1) && isConsensus(ad0)) {
			ad1.doNotOutput = true;
		}
		return 0;
	}

}
