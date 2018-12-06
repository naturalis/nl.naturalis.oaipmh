//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:51:40 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.abcd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Extension for elements only used in plant genetic
 * 				resource collections. Currently, all elements represent EURISCO
 * 				descriptors.
 * 			
 * 
 * <p>Java class for PGRUnit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PGRUnit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NationalInventoryCode" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="BreedingInstitutionCode" type="{http://www.tdwg.org/schemas/abcd/2.06}FAOInstituteCode" minOccurs="0"/>
 *         &lt;element name="BreedingCountryCode" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="BiologicalStatus" type="{http://www.tdwg.org/schemas/abcd/2.06}BiologicalStatus" minOccurs="0"/>
 *         &lt;element name="AncestralData" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL" minOccurs="0"/>
 *         &lt;element name="CollectingAcquisitionSource" type="{http://www.tdwg.org/schemas/abcd/2.06}CollectingAcquisitionSource" minOccurs="0"/>
 *         &lt;element name="OtherIdentification" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="LocationSafetyDuplicates" type="{http://www.tdwg.org/schemas/abcd/2.06}FAOInstituteCode" minOccurs="0"/>
 *         &lt;element name="TypeGermplasmStorage" type="{http://www.tdwg.org/schemas/abcd/2.06}TypeGermplasmStorage" minOccurs="0"/>
 *         &lt;element name="DecodedBreedingInstitute" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="DecodedDonorInstitute" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="DecodedLocationSafetyDuplicates" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="GatheringInstitutionCode" type="{http://www.tdwg.org/schemas/abcd/2.06}FAOInstituteCode" minOccurs="0"/>
 *         &lt;element name="AccessionNames" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AccessionName" type="{http://www.tdwg.org/schemas/abcd/2.06}String" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="AccessionNameText" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PGRUnit", propOrder = {
    "nationalInventoryCode",
    "breedingInstitutionCode",
    "breedingCountryCode",
    "biologicalStatus",
    "ancestralData",
    "collectingAcquisitionSource",
    "otherIdentification",
    "locationSafetyDuplicates",
    "typeGermplasmStorage",
    "decodedBreedingInstitute",
    "decodedDonorInstitute",
    "decodedLocationSafetyDuplicates",
    "gatheringInstitutionCode",
    "accessionNames"
})
public class PGRUnit {

    @XmlElement(name = "NationalInventoryCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String nationalInventoryCode;
    @XmlElement(name = "BreedingInstitutionCode")
    protected String breedingInstitutionCode;
    @XmlElement(name = "BreedingCountryCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String breedingCountryCode;
    @XmlElement(name = "BiologicalStatus")
    protected BigInteger biologicalStatus;
    @XmlElement(name = "AncestralData")
    protected StringL ancestralData;
    @XmlElement(name = "CollectingAcquisitionSource")
    protected BigInteger collectingAcquisitionSource;
    @XmlElement(name = "OtherIdentification")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String otherIdentification;
    @XmlElement(name = "LocationSafetyDuplicates")
    protected String locationSafetyDuplicates;
    @XmlElement(name = "TypeGermplasmStorage")
    protected BigInteger typeGermplasmStorage;
    @XmlElement(name = "DecodedBreedingInstitute")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String decodedBreedingInstitute;
    @XmlElement(name = "DecodedDonorInstitute")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String decodedDonorInstitute;
    @XmlElement(name = "DecodedLocationSafetyDuplicates")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String decodedLocationSafetyDuplicates;
    @XmlElement(name = "GatheringInstitutionCode")
    protected String gatheringInstitutionCode;
    @XmlElement(name = "AccessionNames")
    protected PGRUnit.AccessionNames accessionNames;

    /**
     * Gets the value of the nationalInventoryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationalInventoryCode() {
        return nationalInventoryCode;
    }

    /**
     * Sets the value of the nationalInventoryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationalInventoryCode(String value) {
        this.nationalInventoryCode = value;
    }

    /**
     * Gets the value of the breedingInstitutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBreedingInstitutionCode() {
        return breedingInstitutionCode;
    }

    /**
     * Sets the value of the breedingInstitutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBreedingInstitutionCode(String value) {
        this.breedingInstitutionCode = value;
    }

    /**
     * Gets the value of the breedingCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBreedingCountryCode() {
        return breedingCountryCode;
    }

    /**
     * Sets the value of the breedingCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBreedingCountryCode(String value) {
        this.breedingCountryCode = value;
    }

    /**
     * Gets the value of the biologicalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBiologicalStatus() {
        return biologicalStatus;
    }

    /**
     * Sets the value of the biologicalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBiologicalStatus(BigInteger value) {
        this.biologicalStatus = value;
    }

    /**
     * Gets the value of the ancestralData property.
     * 
     * @return
     *     possible object is
     *     {@link StringL }
     *     
     */
    public StringL getAncestralData() {
        return ancestralData;
    }

    /**
     * Sets the value of the ancestralData property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringL }
     *     
     */
    public void setAncestralData(StringL value) {
        this.ancestralData = value;
    }

    /**
     * Gets the value of the collectingAcquisitionSource property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCollectingAcquisitionSource() {
        return collectingAcquisitionSource;
    }

    /**
     * Sets the value of the collectingAcquisitionSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCollectingAcquisitionSource(BigInteger value) {
        this.collectingAcquisitionSource = value;
    }

    /**
     * Gets the value of the otherIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherIdentification() {
        return otherIdentification;
    }

    /**
     * Sets the value of the otherIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherIdentification(String value) {
        this.otherIdentification = value;
    }

    /**
     * Gets the value of the locationSafetyDuplicates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationSafetyDuplicates() {
        return locationSafetyDuplicates;
    }

    /**
     * Sets the value of the locationSafetyDuplicates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationSafetyDuplicates(String value) {
        this.locationSafetyDuplicates = value;
    }

    /**
     * Gets the value of the typeGermplasmStorage property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTypeGermplasmStorage() {
        return typeGermplasmStorage;
    }

    /**
     * Sets the value of the typeGermplasmStorage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTypeGermplasmStorage(BigInteger value) {
        this.typeGermplasmStorage = value;
    }

    /**
     * Gets the value of the decodedBreedingInstitute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecodedBreedingInstitute() {
        return decodedBreedingInstitute;
    }

    /**
     * Sets the value of the decodedBreedingInstitute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecodedBreedingInstitute(String value) {
        this.decodedBreedingInstitute = value;
    }

    /**
     * Gets the value of the decodedDonorInstitute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecodedDonorInstitute() {
        return decodedDonorInstitute;
    }

    /**
     * Sets the value of the decodedDonorInstitute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecodedDonorInstitute(String value) {
        this.decodedDonorInstitute = value;
    }

    /**
     * Gets the value of the decodedLocationSafetyDuplicates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecodedLocationSafetyDuplicates() {
        return decodedLocationSafetyDuplicates;
    }

    /**
     * Sets the value of the decodedLocationSafetyDuplicates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecodedLocationSafetyDuplicates(String value) {
        this.decodedLocationSafetyDuplicates = value;
    }

    /**
     * Gets the value of the gatheringInstitutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatheringInstitutionCode() {
        return gatheringInstitutionCode;
    }

    /**
     * Sets the value of the gatheringInstitutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatheringInstitutionCode(String value) {
        this.gatheringInstitutionCode = value;
    }

    /**
     * Gets the value of the accessionNames property.
     * 
     * @return
     *     possible object is
     *     {@link PGRUnit.AccessionNames }
     *     
     */
    public PGRUnit.AccessionNames getAccessionNames() {
        return accessionNames;
    }

    /**
     * Sets the value of the accessionNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link PGRUnit.AccessionNames }
     *     
     */
    public void setAccessionNames(PGRUnit.AccessionNames value) {
        this.accessionNames = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="AccessionName" type="{http://www.tdwg.org/schemas/abcd/2.06}String" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="AccessionNameText" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "accessionName",
        "accessionNameText"
    })
    public static class AccessionNames {

        @XmlElement(name = "AccessionName")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> accessionName;
        @XmlElement(name = "AccessionNameText")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String accessionNameText;

        /**
         * Gets the value of the accessionName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accessionName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccessionName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAccessionName() {
            if (accessionName == null) {
                accessionName = new ArrayList<String>();
            }
            return this.accessionName;
        }

        /**
         * Gets the value of the accessionNameText property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccessionNameText() {
            return accessionNameText;
        }

        /**
         * Sets the value of the accessionNameText property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccessionNameText(String value) {
            this.accessionNameText = value;
        }

    }

}