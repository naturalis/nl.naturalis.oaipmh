package nl.naturalis.oaipmh.rest;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.IDENTIFIER;
import static nl.naturalis.oaipmh.api.Argument.METADATA_PREFIX;
import static nl.naturalis.oaipmh.api.Argument.RESUMPTION_TOKEN;
import static nl.naturalis.oaipmh.api.Argument.SET;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;
import static nl.naturalis.oaipmh.api.Argument.VERB;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateFormat;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateFormatter;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormat;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

import nl.naturalis.oaipmh.api.Argument;
import nl.naturalis.oaipmh.api.BadArgumentError;
import nl.naturalis.oaipmh.api.BadResumptionTokenException;
import nl.naturalis.oaipmh.api.BadVerbError;
import nl.naturalis.oaipmh.api.IOAIRepository;
import nl.naturalis.oaipmh.api.IResumptionTokenParser;
import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.api.util.ResumptionToken;

import org.domainobject.util.CollectionUtil;
import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.VerbType;

/**
 * Builds {@link OAIPMHRequest} objects from HTTP URLs.
 * 
 * @author Ayco Holleman
 *
 */
public class RequestBuilder {

	/*
	 * This currently just returns a new instance, but in the future we might
	 * want to retrieve configuration data just before creating the first
	 * instance.
	 */
	public static RequestBuilder newInstance()
	{
		return new RequestBuilder();
	}

	private UriInfo uriInfo;
	private OAIPMHRequest request;
	private List<OAIPMHerrorType> errors;

	private IResumptionTokenParser rtParser;

	private RequestBuilder()
	{
	}

	/**
	 * Sets the resumption token parser used to decompose the resumptionToken
	 * query parameter (if any). You would ordinarily retrieve the parser from
	 * an {@link IOAIRepository} implementation. If this method is not called
	 * before calling {@link #build(UriInfo)}, the {@link ResumptionToken
	 * default parser} is used.
	 * 
	 * @param parser
	 * @return
	 */
	public RequestBuilder setResumptionTokenParser(IResumptionTokenParser parser)
	{
		this.rtParser = parser;
		return this;
	}

	/**
	 * Build the {@code OAIPMHRequest} from the specified {@link UriInfo}
	 * object.
	 * 
	 * @param uriInfo
	 * @return
	 */
	public OAIPMHRequest build(UriInfo uriInfo)
	{
		this.uriInfo = uriInfo;
		this.request = new OAIPMHRequest();
		this.errors = new ArrayList<>();
		request.setRequestUri(uriInfo.getAbsolutePath());
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

	/**
	 * Return the errors encountered while building the {@code OAIPMHRequest}
	 * object.
	 * 
	 * @return
	 */
	public List<OAIPMHerrorType> getErrors()
	{
		return errors;
	}

	private void setVerb()
	{
		String arg = getArg(VERB);
		if (arg == null) {
			errors.add(new BadVerbError());
		}
		else {
			try {
				VerbType verb = VerbType.fromValue(arg);
				request.setVerb(verb);
			}
			catch (IllegalArgumentException e) {
				errors.add(new BadVerbError(arg));
			}
		}
	}

	private void setFrom()
	{
		String arg = getArg(FROM);
		if (arg == null)
			return;
		try {
			Date date = dateTimeFormatter.parse(arg);
			request.setFrom(date);
			request.setDateFormatFrom(dateTimeFormat);
		}
		catch (ParseException e) {
			try {
				Date date = dateFormatter.parse(arg);
				request.setFrom(date);
				request.setDateFormatFrom(dateFormat);
			}
			catch (ParseException e2) {
				errors.add(badDate(arg));
			}
		}
	}

	private void setUntil()
	{
		String arg = getArg(FROM);
		if (arg == null)
			return;
		try {
			Date date = dateTimeFormatter.parse(arg);
			request.setUntil(date);
			request.setDateFormatUntil(dateTimeFormat);
		}
		catch (ParseException e) {
			try {
				Date date = dateFormatter.parse(arg);
				request.setUntil(date);
				request.setDateFormatUntil(dateFormat);
			}
			catch (ParseException e2) {
				errors.add(badDate(arg));
			}
		}
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
				errors.add(new BadArgumentError(msg));
			}
			else {
				List<String> values = uriInfo.getQueryParameters().get(param);
				if (values == null || values.size() == 0) {
					String msg = "Empty argument: " + param;
					errors.add(new BadArgumentError(msg));
				}
				else if (values.size() != 1) {
					String msg = "Duplicate argument: " + param;
					errors.add(new BadArgumentError(msg));
				}
				if (arg != VERB) {
					args.add(arg);
					VerbType verb = request.getVerb();
					if (verb != null && !arg.isAllowed(verb)) {
						String fmt = "Argument %s not allowed for verb %s";
						String msg = String.format(fmt, arg, verb.value());
						errors.add(new BadArgumentError(msg));
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
			errors.add(new BadArgumentError(msg));
		}
	}

	private void processResumptionToken()
	{
		if (request.getResumptionToken() != null) {
			String fmt = "resumptionToken argument cannot be combined with %s argument";
			if (request.getFrom() != null)
				errors.add(new BadArgumentError(String.format(fmt, FROM)));
			if (request.getUntil() != null)
				errors.add(new BadArgumentError(String.format(fmt, UNTIL)));
			if (request.getMetadataPrefix() != null)
				errors.add(new BadArgumentError(String.format(fmt, METADATA_PREFIX)));
			if (request.getSet() != null)
				errors.add(new BadArgumentError(String.format(fmt, SET)));
			if (request.getIdentifier() != null)
				errors.add(new BadArgumentError(String.format(fmt, IDENTIFIER)));
			if (errors.size() == 0) {
				try {
					if (rtParser == null)
						rtParser = new ResumptionToken();
					rtParser.decompose(request);
				}
				catch (BadResumptionTokenException e) {
					errors.add(e.getErrors().get(0));
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

	private static OAIPMHerrorType badDate(String param)
	{
		String fmt = "Invalid or illegal date format for parameter \"%s\" (must be either \"%s\" or \"%s\")";
		String msg = String.format(fmt, dateTimeFormat, dateFormat);
		return new BadArgumentError(msg);
	}

}
