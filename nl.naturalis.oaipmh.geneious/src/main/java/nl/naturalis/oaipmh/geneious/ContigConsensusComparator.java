package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.description;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isConsensus;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isContig;

import java.util.Comparator;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances according to
 * whether one can be said to represent a consensus sequence created from the
 * contig sequence represented by the other. Note that we actually only use
 * Java's compare/sort mechanism as a way of getting our hands on each
 * combination of two annotated documents. The
 * {@link #compare(AnnotatedDocument, AnnotatedDocument) compare} method always
 * returns 0, so sortings with this comparator does not change the order of the
 * annotated documents. However, a side effect of calling this method is that
 * one of the two annotated documents passed to it may get marked as
 * discardable. A {@link IAnnotatedDocumentSetFilter set filter} using this
 * comparator to "sort" the annotated documents can subsequently iterate over
 * them and simply discard all annotated documents marked as discardable by the
 * {@code compare} method.
 * 
 * @author Ayco Holleman
 *
 */
public class ContigConsensusComparator implements Comparator<AnnotatedDocument> {

	public ContigConsensusComparator()
	{
	}

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
