package nl.naturalis.oaipmh;

import java.util.Date;

import nl.naturalis.oaipmh.api.Argument;
import nl.naturalis.oaipmh.api.BadResumptionTokenException;
import nl.naturalis.oaipmh.api.OAIPMHRequest;

import org.domainobject.util.ArrayUtil;

import static nl.naturalis.oaipmh.api.Argument.*;

/**
 * Class used to create and decompose OAI-PMH resumption tokens.
 * 
 * @author Ayco Holleman
 *
 */
public class ResumptionToken implements IResumptionToken {

	/*
	 * Since all parts of the resumption token have been converted to
	 * hexadecimal strings, we can use "g" as a delimiter.
	 */
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
	public void decompose(String token, OAIPMHRequest request) throws BadResumptionTokenException
	{
		String[] slices = token.split(DELIMITER);
		if (slices.length != 5)
			throw new BadResumptionTokenException();
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
