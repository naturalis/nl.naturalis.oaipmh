package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.VerbType.LIST_RECORDS;

import java.util.EnumMap;

import org.openarchives.oai._2.VerbType;

/**
 * Creates {@link ArgumentChecker} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class ArgumentCheckerFactory {

	private static ArgumentCheckerFactory instance;

	/**
	 * Returns an instance of an {@link ArgumentCheckerFactory}.
	 * 
	 * @return
	 */
	public static ArgumentCheckerFactory getInstance()
	{
		if (instance == null)
			instance = new ArgumentCheckerFactory();
		return instance;
	}

	private final EnumMap<VerbType, ArgumentChecker> checkers;

	private ArgumentCheckerFactory()
	{
		checkers = new EnumMap<>(VerbType.class);
		checkers.put(LIST_RECORDS, new ListRecordsArgumentChecker());
		// TODO More argument checkers for more verbs
	}

	/**
	 * Creates an {@link ArgumentChecker} instance for the specified verb.
	 * 
	 * @param forVerb
	 * @return
	 */
	public ArgumentChecker createArgumentChecker(VerbType forVerb)
	{
		return checkers.get(forVerb);
	}

}
