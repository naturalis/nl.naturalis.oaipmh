package nl.naturalis.oaipmh.geneious;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import nl.naturalis.oaipmh.util.FileUtil;

import static org.junit.Assert.assertEquals;

import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.cache_name;
import static nl.naturalis.oaipmh.geneious.DocumentHiddenFields.HiddenField.description;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.AmplicificationStaffCode_FixedValue_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.CRSCode_CRS;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractIDCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ExtractPlateNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.MarkerCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.PCRplateIDCode_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.PlatePositionCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.ProjectPlateNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.RegistrationNumberCode_Samples;
import static nl.naturalis.oaipmh.geneious.DocumentNotes.Note.SequencingStaffCode_FixedValue_Seq;
import static nl.naturalis.oaipmh.geneious.DocumentFields.Field.*;

public class DocumentFactoryTest {

  @Test
  public void testCreateDocument() throws IOException {

    GeneiousDocument expected = new GeneiousDocument();
    expected.setDocumentType(GeneiousDocumentType.DefaultNucleotideGraphSequence);
    expected.setReferencedDocuments(Arrays.asList("urn:local:Reinier.Kartowikromo:me-5sr89qb"));
    DocumentNotes notes = new DocumentNotes();
    notes.set(CRSCode_CRS, "true");
    notes.set(RegistrationNumberCode_Samples, "RMNH.INS.556182");
    notes.set(ExtractPlateNumberCode_Samples, "NCBN000678");
    notes.set(ExtractIDCode_Samples, "e4010125023");
    notes.set(PlatePositionCode_Samples, "A-09");
    notes.set(ProjectPlateNumberCode_Samples, "BCP0047-40");
    notes.set(SequencingStaffCode_FixedValue_Seq, "Naturalis Biodiversity Center\n\t\t\t\tLaboratories");
    notes.set(MarkerCode_Seq, "COI");
    notes.set(PCRplateIDCode_Seq, "MJ243");
    notes.set(AmplicificationStaffCode_FixedValue_Samples, "Naturalis Biodiversity Center\n\t\t\t\tLaboratories");
    expected.setNotes(notes);
    DocumentHiddenFields hidden = new DocumentHiddenFields();
    hidden.set(description, "FOO");
    hidden.set(cache_name, "Reads Assembly Contig 10 consensus sequence");
    expected.setHiddenFields(hidden);
    DocumentFields fields = new DocumentFields();
    fields.set(sequence_length, "174");
    fields.set(consensusSequenceLength, "174");
    expected.setFields(fields);

    try (InputStream is = getClass().getResourceAsStream("/document_xml_01.xml")) {
      String xml = FileUtil.getContents(is);
      GeneiousDocument actual = GeneiousDocumentFactory.createDocument(xml);
      assertEquals(expected, actual);
    }
  }

}
