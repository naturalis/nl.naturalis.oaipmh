package nl.naturalis.oaipmh.api;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.METADATA_PREFIX;
import static nl.naturalis.oaipmh.api.Argument.RESUMPTION_TOKEN;
import static nl.naturalis.oaipmh.api.Argument.SET;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;

import java.util.EnumSet;
import java.util.List;

import org.domainobject.util.CollectionUtil;
import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.VerbType;

/**
 * An {@link ArgumentChecker} for the {@link VerbType#LIST_RECORDS LIST_RECORDS}
 * protocol request.
 * 
 * @author Ayco Holleman
 *
 */
public class ListRecordsArgumentChecker extends ArgumentChecker {

	private static final EnumSet<Argument> required;
	private static final EnumSet<Argument> optional;

	static {
		/*
		 * NB the Argument.RESUMPTION_TOKEN is not included as either an
		 * optional or required argument. Since it is an "exclusive argument"
		 * (it is mutually exclusive with all other arguments except the verb
		 * argument), we deal with it in the beforeCheck() method.
		 */
		required = EnumSet.of(METADATA_PREFIX);
		optional = EnumSet.of(FROM, UNTIL, SET);
	}

	public ListRecordsArgumentChecker()
	{
	}

	@Override
	protected boolean beforeCheck(EnumSet<Argument> arguments, List<OAIPMHerrorType> errors)
	{
		if (arguments.contains(RESUMPTION_TOKEN)) {
			if (arguments.size() != 1) {
				EnumSet<Argument> copy = EnumSet.copyOf(arguments);
				copy.remove(RESUMPTION_TOKEN);
				String illegal = CollectionUtil.implode(copy, "/");
				String fmt = "resumptionToken argument cannot be combined with %s argument";
				String msg = String.format(fmt, illegal);
				errors.add(new BadArgumentError(msg));
			}
			return false;
		}
		return true;
	}

	@Override
	protected EnumSet<Argument> getRequiredArguments()
	{
		return required;
	}

	@Override
	protected EnumSet<Argument> getOptionalArguments()
	{
		return optional;
	}

}
