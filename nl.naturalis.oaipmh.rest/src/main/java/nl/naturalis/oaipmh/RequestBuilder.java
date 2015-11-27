package nl.naturalis.oaipmh;

import static nl.naturalis.oaipmh.Argument.FROM;
import static nl.naturalis.oaipmh.Argument.IDENTIFIER;
import static nl.naturalis.oaipmh.Argument.METADATA_PREFIX;
import static nl.naturalis.oaipmh.Argument.RESUMPTION_TOKEN;
import static nl.naturalis.oaipmh.Argument.SET;
import static nl.naturalis.oaipmh.Argument.UNTIL;
import static nl.naturalis.oaipmh.Argument.VERB;
import static nl.naturalis.oaipmh.OAIPMHException.createError;
import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_ARGUMENT;
import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_RESUMPTION_TOKEN;
import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_VERB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

import nl.naturalis.oaipmh.rest.OAIPMHResource;

import org.domainobject.util.CollectionUtil;
import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.VerbType;

public class RequestBuilder {

	/**
	 * Datetime pattern used for the XML response and possibly also by clients
	 * in the request URL.
	 */
	public static final String OAI_DATETIME_PATTERN;
	/**
	 * Formats dates according to {@link #OAI_DATETIME_PATTERN}.
	 */
	public static final SimpleDateFormat oaiDateTimeFormatter;
	/**
	 * Datetime pattern possibly used by clients in the request URL.
	 */
	public static final String OAI_DATE_PATTERN;
	/**
	 * Formats dates according to {@link #OAI_DATE_PATTERN}.
	 */
	public static final SimpleDateFormat oaiDateFormatter;

	static {
		OAI_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		OAI_DATE_PATTERN = "yyyy-MM-dd";
		oaiDateTimeFormatter = new SimpleDateFormat(OAI_DATETIME_PATTERN);
		oaiDateFormatter = new SimpleDateFormat(OAI_DATE_PATTERN);
	}

	public static RequestBuilder newInstance()
	{
		// TODO Load config data once, e.g. specifying the _ResumptionToken impl
		// class
		return new RequestBuilder();
	}

	private UriInfo uriInfo;
	private OAIPMHRequest request;
	private List<OAIPMHerrorType> errors;

	// TODO Retrieve & inject from outside somehow
	private IResumptionToken resToken = new ResumptionToken();

	private RequestBuilder()
	{
	}

	public OAIPMHRequest build(UriInfo uriInfo)
	{
		this.uriInfo = uriInfo;
		this.request = new OAIPMHRequest();
		this.errors = new ArrayList<>();
		request.setMetadataPrefix(getArg(METADATA_PREFIX));
		request.setIdentifier(getArg(IDENTIFIER));
		request.setSet(getArg(SET));
		request.setResumptionToken(getArg(RESUMPTION_TOKEN));
		setVerb();
		setFrom();
		setUntil();
		Set<Argument> args = checkArguments();
		checkRequiredArguments(args);
		processResumptionToken();
		return request;
	}

	public List<OAIPMHerrorType> getErrors()
	{
		return errors;
	}

	private void setVerb()
	{
		String arg = getArg(VERB);
		if (arg == null) {
			errors.add(createError(BAD_VERB, "Missing verb"));
		}
		else {
			try {
				VerbType verb = VerbType.fromValue(arg);
				request.setVerb(verb);
			}
			catch (IllegalArgumentException e) {
				errors.add(createError(BAD_VERB, "Bad verb: " + arg));
			}
		}
	}

	private void setFrom()
	{
		String arg = getArg(FROM);
		if (arg == null)
			return;
		Date from = parseDate(arg);
		if (from == null)
			errors.add(badDate(arg));
		else
			request.setFrom(from);
	}

	private void setUntil()
	{
		String arg = getArg(UNTIL);
		if (arg == null)
			return;
		Date until = parseDate(arg);
		if (until == null)
			errors.add(badDate(arg));
		else
			request.setUntil(until);
	}

	/*
	 * Check for duplicate and illegal parameters. Returns all valid OAIPMH
	 * arguments in the request except the verb, which is more like the name of
	 * the function to be executed.
	 */
	private Set<Argument> checkArguments()
	{
		Set<Argument> args = new HashSet<>(5);
		for (String param : uriInfo.getQueryParameters().keySet()) {
			Argument arg = Argument.parse(param);
			if (arg == null) {
				String msg = "Illegal argument: " + param;
				errors.add(createError(BAD_ARGUMENT, msg));
			}
			else {
				List<String> values = uriInfo.getQueryParameters().get(param);
				if (values == null || values.size() == 0) {
					String msg = "Empty argument: " + param;
					errors.add(createError(BAD_ARGUMENT, msg));
				}
				else if (values.size() != 1) {
					String msg = "Duplicate argument: " + param;
					errors.add(createError(BAD_ARGUMENT, msg));
				}
				if (arg != VERB) {
					args.add(arg);
					VerbType verb = request.getVerb();
					if (verb != null && !arg.isOptional(verb)) {
						String fmt = "Argument %s not allowed for verb %s";
						String msg = String.format(fmt, arg, verb.value());
						errors.add(createError(BAD_ARGUMENT, msg));
					}
				}
			}
		}
		return args;
	}

	private void checkRequiredArguments(Set<Argument> provided)
	{
		VerbType verb = request.getVerb();
		if (verb == null)
			return;
		Set<Argument> required = Argument.getRequiredArguments(verb);
		required.removeAll(provided);
		if (required.size() != 0) {
			String missing = CollectionUtil.implode(required);
			String msg = "Missing required argument(s): " + missing;
			errors.add(createError(BAD_ARGUMENT, msg));
		}
	}

	private void processResumptionToken()
	{
		String token = getArg(RESUMPTION_TOKEN);
		if (request.getResumptionToken() != null) {
			String fmt = "resumptionToken argument cannot be combined with %s argument";
			if (request.getFrom() != null)
				errors.add(createError(BAD_ARGUMENT, String.format(fmt, FROM)));
			if (request.getUntil() != null)
				errors.add(createError(BAD_ARGUMENT, String.format(fmt, UNTIL)));
			if (request.getMetadataPrefix() != null)
				errors.add(createError(BAD_ARGUMENT, String.format(fmt, METADATA_PREFIX)));
			if (request.getSet() != null)
				errors.add(createError(BAD_ARGUMENT, String.format(fmt, SET)));
			if (request.getIdentifier() != null)
				errors.add(createError(BAD_ARGUMENT, String.format(fmt, IDENTIFIER)));
			if (errors.size() == 0) {
				try {
					resToken.decompose(token, request);
				}
				catch (BadResumptionTokenException e) {
					errors.add(createError(BAD_RESUMPTION_TOKEN, e.getMessage()));
				}
			}
		}
	}

	private String getArg(Argument arg)
	{
		String s = uriInfo.getQueryParameters().getFirst(arg.param());
		if (s == null || s.length() == 0)
			return null;
		return s;
	}

	private static Date parseDate(String value)
	{
		try {
			return oaiDateTimeFormatter.parse(value);
		}
		catch (ParseException e) {
			try {
				return oaiDateFormatter.parse(value);
			}
			catch (ParseException e2) {
				return null;
			}
		}
	}

	private static OAIPMHerrorType badDate(String param)
	{
		OAIPMHerrorType error = OAIPMHResource.OAI_FACTORY.createOAIPMHerrorType();
		String fmt = "Bad format for parameter \"%s\" (must be either \"%s\" or \"%s\")";
		String msg = String.format(fmt, OAI_DATETIME_PATTERN, OAI_DATE_PATTERN);
		error.setCode(BAD_ARGUMENT);
		error.setValue(msg);
		return error;
	}

}
