//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.10 at 12:34:04 PM CET 
//


package nl.naturalis.oaipmh.geneious.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneticAccession complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeneticAccession">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="geneticAccessionNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="geneticAccessionNumberURI" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="BOLDProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneticAccession", propOrder = {
    "geneticAccessionNumber",
    "geneticAccessionNumberURI",
    "boldProcessID"
})
public class GeneticAccession {

    @XmlElement(required = true)
    protected String geneticAccessionNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String geneticAccessionNumberURI;
    @XmlElement(name = "BOLDProcessID", required = true)
    protected String boldProcessID;

    /**
     * Gets the value of the geneticAccessionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneticAccessionNumber() {
        return geneticAccessionNumber;
    }

    /**
     * Sets the value of the geneticAccessionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneticAccessionNumber(String value) {
        this.geneticAccessionNumber = value;
    }

    /**
     * Gets the value of the geneticAccessionNumberURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneticAccessionNumberURI() {
        return geneticAccessionNumberURI;
    }

    /**
     * Sets the value of the geneticAccessionNumberURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneticAccessionNumberURI(String value) {
        this.geneticAccessionNumberURI = value;
    }

    /**
     * Gets the value of the boldProcessID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOLDProcessID() {
        return boldProcessID;
    }

    /**
     * Sets the value of the boldProcessID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOLDProcessID(String value) {
        this.boldProcessID = value;
    }

}
