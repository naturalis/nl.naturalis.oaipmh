package nl.naturalis.oaipmh.api;

import static nl.naturalis.oaipmh.api.Argument.UNTIL;
import static nl.naturalis.oaipmh.api.Argument.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ArgumentTest {

	@Test
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

}
