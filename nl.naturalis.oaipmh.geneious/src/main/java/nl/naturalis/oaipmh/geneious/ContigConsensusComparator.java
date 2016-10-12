package nl.naturalis.oaipmh.geneious;

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

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String descr0 = ad0.getDocument().getDescription();
		String descr1 = ad1.getDocument().getDescription();
		int i = descr0.compareTo(descr1);
		if (i != 0) {
			return 0;
		}
		boolean ad0IsContig = isContig(ad0);
		boolean ad1IsContig = isContig(ad1);
		assert ad0IsContig || ad1IsContig;
		if (ad0IsContig) {
			assert ad1.getPluginDocument() instanceof XMLSerialisableRootElement;
			ad0.doNotOutput = true;
			return 0;
		}
		assert ad0.getPluginDocument() instanceof XMLSerialisableRootElement;
		ad1.doNotOutput = true;
		return 0;
	}

	private static boolean isContig(AnnotatedDocument ad)
	{
		if (ad.getPluginDocument() instanceof DefaultAlignmentDocument) {
			DefaultAlignmentDocument dad = (DefaultAlignmentDocument) ad.getPluginDocument();
			return dad.isContig();
		}
		return false;
	}

}
