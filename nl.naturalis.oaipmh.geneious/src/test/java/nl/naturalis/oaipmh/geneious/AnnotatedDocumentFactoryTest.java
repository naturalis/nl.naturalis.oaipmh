package nl.naturalis.oaipmh.geneious;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;

import org.domainobject.util.FileUtil;
import org.junit.Before;
import org.junit.Test;

public class AnnotatedDocumentFactoryTest {

	private MockResultSet rs;

	@Before
	public void setup() throws ParseException
	{
		rs = new MockResultSet();
		rs.setInt("id", 32321);
		rs.setInt("folder_id", 729);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2016-02-23 12:30:05");
		rs.setLong("modified", date.getTime());
		rs.setString("urn", "urn:local:Reinier.Kartowikromo:my-5sr8a1x");
		InputStream is = getClass().getResourceAsStream("/document_xml_01.xml");
		String xml = FileUtil.getContents(is);
		rs.setString("document_xml", xml);
		is = getClass().getResourceAsStream("/plugin_document_xml_01.xml");
		xml = FileUtil.getContents(is);
		rs.setString("plugin_document_xml", xml);
		rs.setInt("reference_count", 0);

	}

	@Test
	public void testBuild() throws SQLException
	{
		AnnotatedDocumentFactory adf = new AnnotatedDocumentFactory();
		AnnotatedDocument ad = adf.build(rs);
		assertEquals("01", 32321, ad.getId());
		assertEquals("02", 729, ad.getFolderId());
		DocumentNotes notes = ad.getDocument().getNotes();
		assertEquals("03", "RMNH.INS.556182", notes.get(Note.RegistrationNumberCode_Samples));
	}

}
