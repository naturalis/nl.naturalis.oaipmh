package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.description;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isConsensus;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.isContig;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances according to
 * whether one can be said to represent a consensus sequence created from the
 * contig sequence represented by the other.
 * 
 * @author Ayco Holleman
 *
 */
public class ContigConsensusComparator implements Comparator<AnnotatedDocument> {

	private static final Logger logger = LogManager.getLogger(ContigConsensusComparator.class);

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Contig record superseded by consensus record with id {}";

	public ContigConsensusComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String descr0 = ad0.getDocument().getHiddenField(description);
		if (descr0 == null) {
			// Just some negative integer that stands out when debugging
			return -8192;
		}
		String descr1 = ad1.getDocument().getHiddenField(description);
		if (descr1 == null) {
			return 8192;
		}
		int i = descr0.compareTo(descr1);
		if (i == 0) {
			if (isContig(ad0) && isConsensus(ad1)) {
				if (!ad0.doNotOutput && logger.isDebugEnabled()) {
					logger.debug(MSG, ad0.getId(), ad1.getId());
				}
				ad0.doNotOutput = true;
				// Consensus record BEFORE contig record;
				return Integer.MAX_VALUE;
			}
			else if (isContig(ad1) && isConsensus(ad0)) {
				if (!ad1.doNotOutput && logger.isDebugEnabled()) {
					logger.debug(MSG, ad1.getId(), ad0.getId());
				}
				ad1.doNotOutput = true;
				return Integer.MIN_VALUE;
			}
		}
		return i;
	}

}
