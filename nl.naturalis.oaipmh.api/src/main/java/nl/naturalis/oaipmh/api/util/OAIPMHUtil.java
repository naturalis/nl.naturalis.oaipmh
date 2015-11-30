package nl.naturalis.oaipmh.api.util;

import java.text.SimpleDateFormat;

public class OAIPMHUtil {

	/**
	 * Datetime pattern used for the OAI-PMH requests and responses
	 * (yyyy-MM-dd'T'HH:mm:ss'Z').
	 */
	public static final String dateTimeFormat;

	/**
	 * Datetime pattern that can optionally also be used for OAI-PMH requests
	 * (yyyy-MM-dd)
	 */
	public static final String dateFormat;

	/**
	 * Formats dates according to {@link #dateTimeFormat}.
	 */
	public static final SimpleDateFormat dateTimeFormatter;

	/**
	 * Formats dates according to {@link #dateFormat}.
	 */
	public static final SimpleDateFormat dateFormatter;

	static {
		dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		dateFormat = "yyyy-MM-dd";
		dateTimeFormatter = new SimpleDateFormat(dateTimeFormat);
		dateFormatter = new SimpleDateFormat(dateFormat);
	}

	private OAIPMHUtil()
	{
	}

}
