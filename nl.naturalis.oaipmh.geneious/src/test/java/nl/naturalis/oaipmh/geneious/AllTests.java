package nl.naturalis.oaipmh.geneious;

import nl.naturalis.oaipmh.geneious.extracts.DnaExtractFilterTest;
import nl.naturalis.oaipmh.geneious.specimens.SpecimenFilterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnnotatedDocumentFactoryTest.class, DocumentFactoryTest.class,
		SharedPostFilterTest.class, XMLSerialisableRootElementFactoryTest.class,
		DnaExtractFilterTest.class, SpecimenFilterTest.class })
public class AllTests {

}
