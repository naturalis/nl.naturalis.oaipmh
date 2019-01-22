//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:48:39 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.ggbn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Container element for information of a sequencing
 * 				or amplification primer
 * 
 * <p>Java class for primer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="primer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="primerSequence" type="{http://data.ggbn.org/schemas/ggbn/terms}stringDirection" minOccurs="0"/>
 *         &lt;element name="primerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primerReferenceCitation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primerReferenceLink" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="adapters" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "primer", propOrder = {
    "primerSequence",
    "primerName",
    "primerReferenceCitation",
    "primerReferenceLink",
    "adapters",
    "mid"
})
public class Primer {

    protected StringDirection primerSequence;
    protected String primerName;
    protected String primerReferenceCitation;
    @XmlSchemaType(name = "anyURI")
    protected String primerReferenceLink;
    protected String adapters;
    protected String mid;

    /**
     * Gets the value of the primerSequence property.
     * 
     * @return
     *     possible object is
     *     {@link StringDirection }
     *     
     */
    public StringDirection getPrimerSequence() {
        return primerSequence;
    }

    /**
     * Sets the value of the primerSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringDirection }
     *     
     */
    public void setPrimerSequence(StringDirection value) {
        this.primerSequence = value;
    }

    /**
     * Gets the value of the primerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimerName() {
        return primerName;
    }

    /**
     * Sets the value of the primerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimerName(String value) {
        this.primerName = value;
    }

    /**
     * Gets the value of the primerReferenceCitation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimerReferenceCitation() {
        return primerReferenceCitation;
    }

    /**
     * Sets the value of the primerReferenceCitation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimerReferenceCitation(String value) {
        this.primerReferenceCitation = value;
    }

    /**
     * Gets the value of the primerReferenceLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimerReferenceLink() {
        return primerReferenceLink;
    }

    /**
     * Sets the value of the primerReferenceLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimerReferenceLink(String value) {
        this.primerReferenceLink = value;
    }

    /**
     * Gets the value of the adapters property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdapters() {
        return adapters;
    }

    /**
     * Sets the value of the adapters property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdapters(String value) {
        this.adapters = value;
    }

    /**
     * Gets the value of the mid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMid() {
        return mid;
    }

    /**
     * Sets the value of the mid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMid(String value) {
        this.mid = value;
    }

}
