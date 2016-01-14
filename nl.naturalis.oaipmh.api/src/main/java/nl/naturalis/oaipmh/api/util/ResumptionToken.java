package nl.naturalis.oaipmh.api.util;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;

import java.util.Date;

import nl.naturalis.oaipmh.api.Argument;
import nl.naturalis.oaipmh.api.BadResumptionTokenException;
import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.IResumptionTokenParser;
import nl.naturalis.oaipmh.api.IResumptionTokenGenerator;
import nl.naturalis.oaipmh.api.OAIPMHRequest;

import org.domainobject.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of {@link IResumptionTokenParser} and
 * {@link IResumptionTokenGenerator} that is propably suitable for most
 * {@link IOAIRepository} implementations. This implementation encodes/decodes
 * the following information into/from the resumption token:
 * <ol>
 * <li>The {@link OAIPMHRequest#getFrom() from date}
 * <li>The {@link OAIPMHRequest#getUntil() until date}
 * <li>The requested {@link OAIPMHRequest#getSet() data set}
 * <li>The requested {@link OAIPMHRequest#getPage() page}
 * <li>The {@link OAIPMHRequest#getMetadataPrefix() metadataPrefix}
 * </ol>
 * 
 * @author Ayco Holleman
 *
 */
public class ResumptionToken implements IResumptionTokenParser, IResumptionTokenGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ResumptionToken.class);

	private static final String DELIMITER = "^|^";
	private static final int FROM_PART = 0;
	private static final int UNTIL_PART = 1;
	private static final int SET_PART = 2;
	private static final int METADATA_PREFIX_PART = 3;
	private static final int PAGE_PART = 4;

	public ResumptionToken()
	{
	}

	@Override
	public OAIPMHRequest decompose(String resumptionToken) throws BadResumptionTokenException
	{
		OAIPMHRequest request = new OAIPMHRequest();
		request.setResumptionToken(resumptionToken);
		decompose(request);
		return request;
	}

	@Override
	public void decompose(OAIPMHRequest request) throws BadResumptionTokenException
	{
		logger.info("XXXXXXXXXXXXXXXXXXXXX: " + request.getResumptionToken());
		String[] slices = request.getResumptionToken().split(DELIMITER);
		if (slices.length != 5) {
			throw new BadResumptionTokenException("Number of parts: " + slices.length);
		}
		request.setFrom(parseDate(FROM, slices[FROM_PART]));
		request.setUntil(parseDate(UNTIL, slices[UNTIL_PART]));
		if (slices[SET_PART].length() != 0)
			request.setSet(slices[SET_PART]);
		if (slices[METADATA_PREFIX_PART].length() != 0)
			request.setSet(slices[METADATA_PREFIX_PART]);
		try {
			int page = Integer.parseInt(slices[PAGE_PART], 16);
			request.setPage(page);
		}
		catch (NumberFormatException e) {
			String fmt = "Failed to extract page from resumption token (bad number: \"%s\")";
			String msg = String.format(fmt, slices[3]);
			throw new BadResumptionTokenException(msg);
		}
	}

	@Override
	public String compose(OAIPMHRequest request)
	{
		String[] parts = new String[5];
		parts[PAGE_PART] = Integer.toHexString(request.getPage() + 1);
		if (request.getFrom() != null)
			parts[FROM_PART] = Long.toHexString(request.getFrom().getTime());
		if (request.getUntil() != null)
			parts[UNTIL_PART] = Long.toHexString(request.getFrom().getTime());
		if (request.getSet() != null)
			parts[SET_PART] = request.getSet();
		if (request.getMetadataPrefix() != null)
			parts[METADATA_PREFIX_PART] = request.getMetadataPrefix();
		return ArrayUtil.implode(parts, DELIMITER);
	}

	private static Date parseDate(Argument arg, String value) throws BadResumptionTokenException
	{
		if (value.length() == 0)
			return null;
		try {
			return new Date(Long.parseLong(value, 16));
		}
		catch (NumberFormatException e) {
			String fmt = "Failed to extract %s argument from resumption token (bad number: \"%s\")";
			String msg = String.format(fmt, arg, value);
			throw new BadResumptionTokenException(msg);
		}

	}
}
