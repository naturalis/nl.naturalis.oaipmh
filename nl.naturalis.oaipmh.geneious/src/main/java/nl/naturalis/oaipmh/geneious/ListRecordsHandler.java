package nl.naturalis.oaipmh.geneious;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.createResponseSkeleton;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormatter;
import static nl.naturalis.oaipmh.api.util.ObjectFactories.oaiFactory;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.checkRequest;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.connect;
import static nl.naturalis.oaipmh.geneious.GeneiousOAIUtil.disconnect;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.naturalis.oaipmh.api.BadResumptionTokenError;
import nl.naturalis.oaipmh.api.NoRecordsMatchError;
import nl.naturalis.oaipmh.api.OAIPMHException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.RepositoryException;
import nl.naturalis.oaipmh.api.util.ResumptionToken;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.domainobject.util.ConfigObject;
import org.openarchives.oai._2.HeaderType;
import org.openarchives.oai._2.ListRecordsType;
import org.openarchives.oai._2.MetadataType;
import org.openarchives.oai._2.OAIPMHtype;
import org.openarchives.oai._2.RecordType;
import org.openarchives.oai._2.ResumptionTokenType;

/**
 * <p>
 * Abstract base class for handlers for the ListRecords protocol request.
 * </p>
 * <h3>Filtering and sorting</h3>
 * <p>
 * This describes how database records are filtered and sorted as part of the
 * OAI-PMH generation process:
 * <ol>
 * <li>First, a SQL query is issued that only selects records from the
 * annotated_document table whose reference_count column equals 0. See
 * {@link ReferenceComparator} for an explanantion. No ORDER BY clause is used;
 * filters applied subsequently cannot rely on the records being sorted in any
 * particular order.
 * <li>Then the {@link SharedPreFilter} is applied. See
 * {@link IAnnotatedDocumentPreFilter here} for an explanation of pre-filters.
 * <li>Then all repository-specific pre-filters are applied. Any number of
 * additional pre-filters can be defined for specimens, DNA extracts and extract
 * plates.
 * <li>Then the {@link SharedPostFilter} is applied. See
 * {@link IAnnotatedDocumentPostFilter here} for an explanation of post-filters.
 * <li>Then all repository-specific post-filters are applied.
 * <li>Then the {@link DocumentVersionSetFilter} is applied. See
 * {@link IAnnotatedDocumentSetFilter here} for an explanation of set filters.
 * <li>Then all repository-specific set filters are applied.
 * <li>Then the remaining records are sorted in descending order of their
 * database ID.
 * </ol>
 * </p>
 * 
 * @author Ayco Holleman
 *
 */
public abstract class ListRecordsHandler {

	private static final Logger logger = LogManager.getLogger(ListRecordsHandler.class);
	private static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat mysqlDateFormatter = new SimpleDateFormat(
			MYSQL_DATE_FORMAT);

	protected final ConfigObject config;
	protected final OAIPMHRequest request;

	protected List<IAnnotatedDocumentPreFilter> preFilters;
	protected List<IAnnotatedDocumentPostFilter> postFilters;
	protected List<IAnnotatedDocumentSetFilter> setFilters;

	protected List<IAnnotatedDocumentPostProcessor> postProcessors;

	public ListRecordsHandler(ConfigObject config, OAIPMHRequest request)
	{
		this.request = request;
		this.config = config;
		preFilters = new ArrayList<>(4);
		preFilters.add(new SharedPreFilter());
		postFilters = new ArrayList<>(4);
		postFilters.add(new SharedPostFilter());
		setFilters = new ArrayList<>(4);
		postProcessors = new ArrayList<>(4);
	}

	/**
	 * Generate the OAI-PMH.
	 * 
	 * @return
	 * @throws RepositoryException
	 * @throws OAIPMHException
	 */
	public OAIPMHtype handleRequest() throws RepositoryException, OAIPMHException
	{
		checkRequest(request);
		preFilters.addAll(getAnnotatedDocumentPreFilters());
		postFilters.addAll(getAnnotatedDocumentPostFilters());
		setFilters.addAll(getAnnotatedDocumentSetFilters());
		postProcessors.addAll(getAnnotatedDocumentPostProcessors());
		List<AnnotatedDocument> annotatedDocuments = getAnnotatedDocuments();
		if (annotatedDocuments.size() == 0) {
			throw new OAIPMHException(new NoRecordsMatchError());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Converting AnnotatedDocument instances to XML");
			logger.debug("N.B. set log level to TRACE to see generated XML");
		}
		OAIPMHtype root = createResponseSkeleton(request);
		ListRecordsType listRecords = oaiFactory.createListRecordsType();
		root.setListRecords(listRecords);
		int pageSize = getPageSize();
		int offset = request.getPage() * pageSize;
		if (offset >= annotatedDocuments.size()) {
			String msg = "Bad resumption token";
			logger.error(msg);
			throw new OAIPMHException(new BadResumptionTokenError(msg));
		}
		int last = Math.min(annotatedDocuments.size(), offset + pageSize);
		logResultSetInfo(annotatedDocuments.size());
		for (int i = offset; i < last; ++i) {
			addRecord(annotatedDocuments.get(i), listRecords);
		}
		if (last < annotatedDocuments.size()) {
			addResumptionToken(listRecords, annotatedDocuments.size(), offset);
		}
		return root;
	}

	/**
	 * Template method to be implemented by subclasses: provide extra
	 * pre-filters for the particular resource the subclass deals with. The
	 * extra pre-filters are applied <i>after</i> the {@link SharedPreFilter}.
	 */
	protected abstract List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters();

	/**
	 * Template method to be implemented by subclasses: provide extra
	 * post-filters for the particular resource the subclass deals with. The
	 * extra post-filters are applied <i>after</i> the {@link SharedPostFilter}.
	 */
	protected abstract List<IAnnotatedDocumentPostFilter> getAnnotatedDocumentPostFilters();

	/**
	 * Template method to be implemented by subclasses: provide extra set
	 * filters for the particular resource the subclass deals with. The extra
	 * post-filters are applied <i>after</i> the
	 * {@link DocumentVersionSetFilter}.
	 */
	protected abstract List<IAnnotatedDocumentSetFilter> getAnnotatedDocumentSetFilters();

	/**
	 * Template method to be implemented by subclasses: provide processors for
	 * {@link AnnotatedDocument} instances once they have gone through all
	 * filters.
	 * 
	 * @return
	 */
	protected abstract List<IAnnotatedDocumentPostProcessor> getAnnotatedDocumentPostProcessors();

	/**
	 * Template method to be implemented by subclasses: provide the child
	 * element of the &lt;geneious&gt; element.
	 * 
	 * @param geneious
	 * @param ad
	 */
	protected abstract void setMetadata(Geneious geneious, AnnotatedDocument ad);

	/**
	 * Template method to be implemented by concrete subclasses: return the page
	 * size (number of records per request) for the particular resource to
	 * subclass deals with.
	 * 
	 * @return
	 */
	protected abstract int getPageSize();

	/**
	 * Returns the database query retrieving the initial set of records (before
	 * any programmatical filtering). Could be overrided by subclasses should
	 * the need arise.
	 * 
	 * @return
	 */
	protected String getSQLQuery()
	{
		StringBuilder sb = new StringBuilder(1000);
		sb.append("SELECT a.id,a.folder_id,UNIX_TIMESTAMP(a.modified) AS modified,");
		sb.append("\n       urn,document_xml,plugin_document_xml,reference_count");
		sb.append("\n  FROM annotated_document a");
		sb.append("\n  JOIN folder f ON a.folder_id = f.id");
		sb.append("\n WHERE f.visible = 1");
		if (request.getFrom() != null) {
			/*
			 * Column "modified" contains the number of seconds since 01-01-1970
			 * while Date.getTime() returns the number of milliseconds since
			 * 01-01-1970.
			 */
			String s = mysqlDateFormatter.format(request.getFrom());
			sb.append("\n AND a.modified >= '").append(s).append('\'');
			// int unixTimestamp = (int) (request.getFrom().getTime() / 1000);
			// sb.append("\n AND modified >= ").append(unixTimestamp);
		}
		if (request.getUntil() != null) {
			String s = mysqlDateFormatter.format(request.getUntil());
			sb.append("\n AND a.modified <= '").append(s).append('\'');
			// int unixTimestamp = (int) (request.getUntil().getTime() / 1000);
			// sb.append("\n AND modified <= ").append(unixTimestamp);
		}
		return sb.toString();
	}

	private List<AnnotatedDocument> getAnnotatedDocuments() throws RepositoryException
	{
		List<AnnotatedDocument> records = null;
		String sql = getSQLQuery();
		Connection conn = null;
		try {
			conn = connect(config);
			Statement stmt = conn.createStatement();
			if (logger.isDebugEnabled()) {
				logger.debug("Executing query:\n{}", sql);
			}
			ResultSet rs = stmt.executeQuery(sql.toString());
			records = createAnnotatedDocuments(rs);
			rs.close();
		}
		catch (SQLException e) {
			throw new RepositoryException("Error while executing query", e);
		}
		finally {
			disconnect(conn);
		}
		for (IAnnotatedDocumentSetFilter setFilter : setFilters) {
			records = setFilter.filter(records);
		}
		if (logger.isDebugEnabled()) {
			String arg0 = DatabaseIDComparator.class.getSimpleName();
			logger.debug("Sorting instances using {}", arg0);
		}
		Collections.sort(records, new DatabaseIDComparator());
		return records;
	}

	private List<AnnotatedDocument> createAnnotatedDocuments(ResultSet rs) throws SQLException
	{
		List<AnnotatedDocument> records = new ArrayList<>(255);
		AnnotatedDocumentFactory factory = new AnnotatedDocumentFactory();
		int numRows = 0;
		LOOP: while (rs.next()) {
			numRows++;
			if (logger.isDebugEnabled()) {
				logger.debug("Processing record with id {}", rs.getInt("id"));
			}
			for (IAnnotatedDocumentPreFilter preFilter : preFilters) {
				if (!preFilter.accept(rs)) {
					continue LOOP;
				}
			}
			AnnotatedDocument record = factory.build(rs);
			for (IAnnotatedDocumentPostFilter postFilter : postFilters) {
				if (!postFilter.accept(record)) {
					continue LOOP;
				}
			}
			records.add(record);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Number of records retrieved from database: {}", numRows);
			String filter;
			for (IAnnotatedDocumentPreFilter pf : preFilters) {
				filter = pf.getClass().getSimpleName();
				logger.debug("Number of records discarded by {}: {}", filter, pf.getNumDiscarded());
				logger.debug("Number of records accepted by {}: {}", filter, pf.getNumAccepted());
			}
			for (IAnnotatedDocumentPostFilter pf : postFilters) {
				filter = pf.getClass().getSimpleName();
				logger.debug("Number of AnnotatedDocument instances discarded by {}: {}", filter,
						pf.getNumDiscarded());
				logger.debug("Number of AnnotatedDocument instances accepted by {}: {}", filter,
						pf.getNumAccepted());
			}
			logger.debug("Number of AnnotatedDocument instances remaining: {}", records.size());
		}
		return records;
	}

	private void addResumptionToken(ListRecordsType listRecords, int numRecords, int offset)
	{
		ResumptionTokenType resumptionToken = oaiFactory.createResumptionTokenType();
		listRecords.setResumptionToken(resumptionToken);
		resumptionToken.setCompleteListSize(BigInteger.valueOf(numRecords));
		resumptionToken.setCursor(BigInteger.valueOf(offset));
		ResumptionToken tokenGenerator = new ResumptionToken();
		String token = tokenGenerator.compose(request);
		resumptionToken.setValue(token);
	}

	private void addRecord(AnnotatedDocument ad, ListRecordsType listRecords)
			throws PostProcessingException
	{
		RecordType record = oaiFactory.createRecordType();
		listRecords.getRecord().add(record);
		record.setHeader(createHeader(ad));
		record.setMetadata(createMetadata(ad));
		for (IAnnotatedDocumentPostProcessor processor : postProcessors) {
			processor.process(record, ad);
		}
	}

	private static HeaderType createHeader(AnnotatedDocument ad)
	{
		HeaderType header = oaiFactory.createHeaderType();
		header.setIdentifier(String.valueOf(ad.getId()));
		long modified = 1000L * ad.getModified();
		header.setDatestamp(dateTimeFormatter.format(new Date(modified)));
		return header;
	}

	private MetadataType createMetadata(AnnotatedDocument ad)
	{
		MetadataType metadata = oaiFactory.createMetadataType();
		Geneious geneious = new Geneious();
		metadata.setAny(geneious);
		setMetadata(geneious, ad);
		return metadata;
	}

	private void logResultSetInfo(int resultSetSize)
	{
		logger.info("Records satisfying request: " + resultSetSize);
		if (logger.isDebugEnabled()) {
			int pageSize = getPageSize();
			int offset = request.getPage() * pageSize;
			int recordsToGo = Math.max(0, resultSetSize - offset - pageSize);
			int requestsToGo = (int) Math.ceil(recordsToGo / pageSize);
			logger.debug("Records served per request: " + pageSize);
			logger.debug("Remaining records: " + recordsToGo);
			String fmt = "%s more request%s needed for full harvest";
			String plural = requestsToGo == 1 ? "" : "s";
			logger.debug(String.format(fmt, requestsToGo, plural));
		}
	}
}
