package nl.naturalis.lims2.oaipmh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnnotatedDocumentFactoryTest.class, DocumentFactoryTest.class,
		SharedPostFilterTest.class, XMLSerialisableRootElementFactoryTest.class })
public class AllTests {

}
