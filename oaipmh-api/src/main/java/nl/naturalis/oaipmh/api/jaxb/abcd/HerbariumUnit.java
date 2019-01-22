//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:51:40 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.abcd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Elements only used for herbarium units
 * 			
 * 
 * <p>Java class for HerbariumUnit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HerbariumUnit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Exsiccatum" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="DuplicatesDistributedTo" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="LoanIdentifier" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="LoanSequenceNo" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LoanDestination" type="{http://www.tdwg.org/schemas/abcd/2.06}InstitutionCode" minOccurs="0"/>
 *         &lt;element name="LoanForBotanist" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="LoanDespatchMethod" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="LoanDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="LoanReturnDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="NaturalOccurrence" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL" minOccurs="0"/>
 *         &lt;element name="CultivatedOccurrence" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HerbariumUnit", propOrder = {
    "exsiccatum",
    "duplicatesDistributedTo",
    "loanIdentifier",
    "loanSequenceNo",
    "loanDestination",
    "loanForBotanist",
    "loanDespatchMethod",
    "loanDate",
    "loanReturnDate",
    "naturalOccurrence",
    "cultivatedOccurrence"
})
public class HerbariumUnit {

    @XmlElement(name = "Exsiccatum")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String exsiccatum;
    @XmlElement(name = "DuplicatesDistributedTo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String duplicatesDistributedTo;
    @XmlElement(name = "LoanIdentifier")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String loanIdentifier;
    @XmlElement(name = "LoanSequenceNo")
    protected BigInteger loanSequenceNo;
    @XmlElement(name = "LoanDestination")
    protected String loanDestination;
    @XmlElement(name = "LoanForBotanist")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String loanForBotanist;
    @XmlElement(name = "LoanDespatchMethod")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String loanDespatchMethod;
    @XmlElement(name = "LoanDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loanDate;
    @XmlElement(name = "LoanReturnDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loanReturnDate;
    @XmlElement(name = "NaturalOccurrence")
    protected StringL naturalOccurrence;
    @XmlElement(name = "CultivatedOccurrence")
    protected StringL cultivatedOccurrence;

    /**
     * Gets the value of the exsiccatum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExsiccatum() {
        return exsiccatum;
    }

    /**
     * Sets the value of the exsiccatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExsiccatum(String value) {
        this.exsiccatum = value;
    }

    /**
     * Gets the value of the duplicatesDistributedTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplicatesDistributedTo() {
        return duplicatesDistributedTo;
    }

    /**
     * Sets the value of the duplicatesDistributedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplicatesDistributedTo(String value) {
        this.duplicatesDistributedTo = value;
    }

    /**
     * Gets the value of the loanIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanIdentifier() {
        return loanIdentifier;
    }

    /**
     * Sets the value of the loanIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanIdentifier(String value) {
        this.loanIdentifier = value;
    }

    /**
     * Gets the value of the loanSequenceNo property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLoanSequenceNo() {
        return loanSequenceNo;
    }

    /**
     * Sets the value of the loanSequenceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLoanSequenceNo(BigInteger value) {
        this.loanSequenceNo = value;
    }

    /**
     * Gets the value of the loanDestination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanDestination() {
        return loanDestination;
    }

    /**
     * Sets the value of the loanDestination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanDestination(String value) {
        this.loanDestination = value;
    }

    /**
     * Gets the value of the loanForBotanist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanForBotanist() {
        return loanForBotanist;
    }

    /**
     * Sets the value of the loanForBotanist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanForBotanist(String value) {
        this.loanForBotanist = value;
    }

    /**
     * Gets the value of the loanDespatchMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanDespatchMethod() {
        return loanDespatchMethod;
    }

    /**
     * Sets the value of the loanDespatchMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanDespatchMethod(String value) {
        this.loanDespatchMethod = value;
    }

    /**
     * Gets the value of the loanDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoanDate() {
        return loanDate;
    }

    /**
     * Sets the value of the loanDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoanDate(XMLGregorianCalendar value) {
        this.loanDate = value;
    }

    /**
     * Gets the value of the loanReturnDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoanReturnDate() {
        return loanReturnDate;
    }

    /**
     * Sets the value of the loanReturnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoanReturnDate(XMLGregorianCalendar value) {
        this.loanReturnDate = value;
    }

    /**
     * Gets the value of the naturalOccurrence property.
     * 
     * @return
     *     possible object is
     *     {@link StringL }
     *     
     */
    public StringL getNaturalOccurrence() {
        return naturalOccurrence;
    }

    /**
     * Sets the value of the naturalOccurrence property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringL }
     *     
     */
    public void setNaturalOccurrence(StringL value) {
        this.naturalOccurrence = value;
    }

    /**
     * Gets the value of the cultivatedOccurrence property.
     * 
     * @return
     *     possible object is
     *     {@link StringL }
     *     
     */
    public StringL getCultivatedOccurrence() {
        return cultivatedOccurrence;
    }

    /**
     * Sets the value of the cultivatedOccurrence property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringL }
     *     
     */
    public void setCultivatedOccurrence(StringL value) {
        this.cultivatedOccurrence = value;
    }

}
