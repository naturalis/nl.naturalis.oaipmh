package nl.naturalis.oaipmh.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ArgumentTest {

	@Test
	public void testParse()
	{
		Argument arg = Argument.parse(null);
		assertNull("01", arg);
		arg = Argument.parse("");
		assertNull("02", arg);
		arg = Argument.parse("bla");
		assertNull("03", arg);
		arg = Argument.parse("UNTIL");
		assertNull("04", arg);
		arg = Argument.parse("until");
		assertEquals("05", Argument.UNTIL, arg);
	}

	@Test
	public void testGetRequiredArguments()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsRequired()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsOptional()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testIsAllowed()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testParam()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testToString()
	{
		fail("Not yet implemented");
	}

}
