package nl.naturalis.oaipmh.geneious;

import nl.naturalis.oaipmh.geneious.extracts.DnaExtractFilterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnnotatedDocumentFactoryTest.class, DocumentFactoryTest.class,
		SharedPostFilterTest.class, SharedSetFilterTest.class,
		XMLSerialisableRootElementFactoryTest.class, DnaExtractFilterTest.class })
public class AllTests {

}
