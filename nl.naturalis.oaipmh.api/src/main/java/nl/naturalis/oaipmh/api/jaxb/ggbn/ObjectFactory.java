//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:48:39 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.ggbn;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nl.naturalis.oaipmh.api.jaxb.ggbn package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nl.naturalis.oaipmh.api.jaxb.ggbn
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GGBN }
     * 
     */
    public GGBN createGGBN() {
        return new GGBN();
    }

    /**
     * Create an instance of {@link SingleSequencing }
     * 
     */
    public SingleSequencing createSingleSequencing() {
        return new SingleSequencing();
    }

    /**
     * Create an instance of {@link Sequencing }
     * 
     */
    public Sequencing createSequencing() {
        return new Sequencing();
    }

    /**
     * Create an instance of {@link Amplification }
     * 
     */
    public Amplification createAmplification() {
        return new Amplification();
    }

    /**
     * Create an instance of {@link StringUnit }
     * 
     */
    public StringUnit createStringUnit() {
        return new StringUnit();
    }

    /**
     * Create an instance of {@link GGBN.GelImage }
     * 
     */
    public GGBN.GelImage createGGBNGelImage() {
        return new GGBN.GelImage();
    }

    /**
     * Create an instance of {@link GGBN.Amplifications }
     * 
     */
    public GGBN.Amplifications createGGBNAmplifications() {
        return new GGBN.Amplifications();
    }

    /**
     * Create an instance of {@link Primer }
     * 
     */
    public Primer createPrimer() {
        return new Primer();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link StringType }
     * 
     */
    public StringType createStringType() {
        return new StringType();
    }

    /**
     * Create an instance of {@link StringDirection }
     * 
     */
    public StringDirection createStringDirection() {
        return new StringDirection();
    }

    /**
     * Create an instance of {@link SingleSequencing.SequencingPrimers }
     * 
     */
    public SingleSequencing.SequencingPrimers createSingleSequencingSequencingPrimers() {
        return new SingleSequencing.SequencingPrimers();
    }

    /**
     * Create an instance of {@link Sequencing.CloningPrimers }
     * 
     */
    public Sequencing.CloningPrimers createSequencingCloningPrimers() {
        return new Sequencing.CloningPrimers();
    }

    /**
     * Create an instance of {@link Sequencing.SingleSequencings }
     * 
     */
    public Sequencing.SingleSequencings createSequencingSingleSequencings() {
        return new Sequencing.SingleSequencings();
    }

    /**
     * Create an instance of {@link Sequencing.GeneticAccession }
     * 
     */
    public Sequencing.GeneticAccession createSequencingGeneticAccession() {
        return new Sequencing.GeneticAccession();
    }

    /**
     * Create an instance of {@link Sequencing.References }
     * 
     */
    public Sequencing.References createSequencingReferences() {
        return new Sequencing.References();
    }

    /**
     * Create an instance of {@link Amplification.Sequencings }
     * 
     */
    public Amplification.Sequencings createAmplificationSequencings() {
        return new Amplification.Sequencings();
    }

    /**
     * Create an instance of {@link Amplification.AmplificationPrimers }
     * 
     */
    public Amplification.AmplificationPrimers createAmplificationAmplificationPrimers() {
        return new Amplification.AmplificationPrimers();
    }

}
