package nl.naturalis.oaipmh.geneious;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.DocumentVersionCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;


/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that, extract ID
 * and marker being equal, selects the instance with the highest
 * {@link DocumentNotes.Note#DocumentVersionCode_Seq document version}.
 * 
 * @see ContigConsensusComparator
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentVersionComparator implements Comparator<AnnotatedDocument> {

	private static final Logger logger = LoggerFactory.getLogger(DocumentVersionComparator.class);

	private static final String MSG = "Record with id {} marked for removal. "
			+ "Obsolete document version ({}). Record superseded by record with id {}";

	public DocumentVersionComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		String e0 = ad0.getDocument().getNote(ExtractIDCode_Samples);
		String e1 = ad1.getDocument().getNote(ExtractIDCode_Samples);
		int i = e0.compareTo(e1);
		if (i == 0) {
			String m0 = ad0.getDocument().getNote(MarkerCode_Seq);
			String m1 = ad1.getDocument().getNote(MarkerCode_Seq);
			i = m0.compareTo(m1);
			if (i == 0) {
				String s0 = ad0.getDocument().getNote(DocumentVersionCode_Seq);
				String s1 = ad1.getDocument().getNote(DocumentVersionCode_Seq);
				int v0 = Integer.parseInt(s0);
				int v1 = Integer.parseInt(s1);
				// Higher document versions BEFORE lower document versions
				i = v1 - v0;
				if (i < 0) {
					// Then version0 > version1; remove ad1
					if (!ad1.doNotOutput && logger.isDebugEnabled()) {
						logger.debug(MSG, ad1.getId(), v0, ad0.getId());
					}
					ad1.doNotOutput = true;
				}
				else if (i > 0) {
					if (!ad0.doNotOutput && logger.isDebugEnabled()) {
						logger.debug(MSG, ad0.getId(), v1, ad1.getId());
					}
					ad0.doNotOutput = true;
				}
				else {
					String fmt = "Illegal duplication in records with ids {} and {}: [{}|{}|{}]";
					logger.error(fmt, ad0.getId(), ad1.getId(), e0, m0, v0);
				}
			}
		}
		return i;
	}
}
