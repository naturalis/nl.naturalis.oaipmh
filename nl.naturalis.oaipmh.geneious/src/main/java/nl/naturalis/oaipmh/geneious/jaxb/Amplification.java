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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Amplification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Amplification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="marker" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amplificationStaff" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pcrPlateID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Amplification", propOrder = {
    "marker",
    "amplificationStaff",
    "pcrPlateID"
})
public class Amplification {

    @XmlElement(required = true)
    protected String marker;
    @XmlElement(required = true)
    protected String amplificationStaff;
    @XmlElement(required = true)
    protected String pcrPlateID;

    /**
     * Gets the value of the marker property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarker() {
        return marker;
    }

    /**
     * Sets the value of the marker property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarker(String value) {
        this.marker = value;
    }

    /**
     * Gets the value of the amplificationStaff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmplificationStaff() {
        return amplificationStaff;
    }

    /**
     * Sets the value of the amplificationStaff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmplificationStaff(String value) {
        this.amplificationStaff = value;
    }

    /**
     * Gets the value of the pcrPlateID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcrPlateID() {
        return pcrPlateID;
    }

    /**
     * Sets the value of the pcrPlateID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcrPlateID(String value) {
        this.pcrPlateID = value;
    }

}
