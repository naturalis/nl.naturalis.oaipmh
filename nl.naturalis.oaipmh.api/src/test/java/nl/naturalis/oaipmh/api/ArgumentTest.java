package nl.naturalis.oaipmh.api;

import static nl.naturalis.oaipmh.api.Argument.FROM;
import static nl.naturalis.oaipmh.api.Argument.IDENTIFIER;
import static nl.naturalis.oaipmh.api.Argument.METADATA_PREFIX;
import static nl.naturalis.oaipmh.api.Argument.UNTIL;
import static nl.naturalis.oaipmh.api.Argument.getRequiredArguments;
import static nl.naturalis.oaipmh.api.Argument.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.openarchives.oai._2.VerbType.LIST_RECORDS;

import java.util.Set;

import org.junit.Test;

public class ArgumentTest {

	@Test
	@SuppressWarnings("static-method")
	public void testParse()
	{
		Argument arg = parse(null);
		assertNull("01", arg);
		arg = parse("");
		assertNull("02", arg);
		arg = parse("bla");
		assertNull("03", arg);
		arg = parse("UNTIL");
		assertNull("04", arg);
		arg = parse("until");
		assertEquals("05", UNTIL, arg);
	}

	@Test
	@SuppressWarnings("static-method")
	public void testGetRequiredArguments()
	{
		Set<Argument> args = getRequiredArguments(LIST_RECORDS);
		assertEquals("01", 0, args.size());
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsRequired()
	{
		assertFalse("01", IDENTIFIER.isRequired(LIST_RECORDS));
		assertFalse("02", FROM.isRequired(LIST_RECORDS));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsOptional()
	{
		assertTrue("01", METADATA_PREFIX.isOptional(LIST_RECORDS));
		assertFalse("02", IDENTIFIER.isOptional(LIST_RECORDS));
		assertTrue("03", FROM.isOptional(LIST_RECORDS));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsArgumentFor()
	{
		assertTrue("01", METADATA_PREFIX.isArgumentFor(LIST_RECORDS));
		assertFalse("02", IDENTIFIER.isArgumentFor(LIST_RECORDS));
		assertTrue("03", FROM.isArgumentFor(LIST_RECORDS));
	}

}
