package nl.naturalis.oaipmh.geneious.plates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements both pre and post filtering for DNA extractplates. Only the method
 * specified by {@link IAnnotatedDocumentPreFilter} actually contains filter
 * logic. The method specified by {@link IAnnotatedDocumentPostFilter} currently
 * just returns {@code true}.
 * 
 * @author Ayco Holleman
 *
 */
public class DnaExtractPlateFilter implements IAnnotatedDocumentPostFilter,
		IAnnotatedDocumentPreFilter {

	private static final Logger logger = LogManager.getLogger(DnaExtractPlateFilter.class);

	private final HashSet<String> plateIDs;

	public DnaExtractPlateFilter()
	{
		plateIDs = new HashSet<>(50);
	}

	@Override
	public boolean accept(ResultSet rs) throws SQLException
	{
		// Some bare-knuckle XML parsing here for fail-fast processing
		String xml = rs.getString("document_xml");
		if (xml.indexOf("<ExtractPlateNumberCode_Samples>") == -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: document_xml column does not contain string \"<ExtractPlateNumberCode_Samples>\"");
			}
			return false;
		}
		return true;
	}

	/**
	 * Whether or not to filter out the provided {@link AnnotatedDocument}. N.B.
	 * this implementation relies on the fact that the {@code getSQLQuery}
	 * method of {@link ListRecordsHandler} returns records sorted by their
	 * database ID in descending order.
	 */
	@Override
	public boolean accept(AnnotatedDocument ad)
	{
		String plateID = ad.getDocument().getNotes().get(Note.ExtractPlateNumberCode_Samples);
		if (plateIDs.contains(plateID)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Record discarded: plate with ID \"{}\" already processed");
			}
			return false;
		}
		plateIDs.add(plateID);
		return true;
	}

}
