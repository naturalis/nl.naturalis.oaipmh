//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:51:40 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.abcd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Elements specific to units from botanical gardens
 * 			
 * 
 * <p>Java class for BotanicalGardenUnit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BotanicalGardenUnit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccessionSpecimenNumbers" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="AccessionStatus" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="LocationInGarden" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="AccessionMaterialType" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="Hardiness" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProvenanceCategory" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="PropagationHistoryCode" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="AccessionLineage" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="DonorCategory" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="Cultivation" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="PlantingDate" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="Propagation" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="Perennation" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="BreedingSystem" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="IPEN" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BotanicalGardenUnit", propOrder = {
    "accessionSpecimenNumbers",
    "accessionStatus",
    "locationInGarden",
    "accessionMaterialType",
    "hardiness",
    "provenanceCategory",
    "propagationHistoryCode",
    "accessionLineage",
    "donorCategory",
    "cultivation",
    "plantingDate",
    "propagation",
    "perennation",
    "breedingSystem",
    "ipen"
})
public class BotanicalGardenUnit {

    @XmlElement(name = "AccessionSpecimenNumbers")
    protected Integer accessionSpecimenNumbers;
    @XmlElement(name = "AccessionStatus")
    protected Byte accessionStatus;
    @XmlElement(name = "LocationInGarden")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String locationInGarden;
    @XmlElement(name = "AccessionMaterialType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String accessionMaterialType;
    @XmlElement(name = "Hardiness")
    protected Boolean hardiness;
    @XmlElement(name = "ProvenanceCategory")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String provenanceCategory;
    @XmlElement(name = "PropagationHistoryCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String propagationHistoryCode;
    @XmlElement(name = "AccessionLineage")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String accessionLineage;
    @XmlElement(name = "DonorCategory")
    protected Byte donorCategory;
    @XmlElement(name = "Cultivation")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cultivation;
    @XmlElement(name = "PlantingDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String plantingDate;
    @XmlElement(name = "Propagation")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String propagation;
    @XmlElement(name = "Perennation")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String perennation;
    @XmlElement(name = "BreedingSystem")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String breedingSystem;
    @XmlElement(name = "IPEN")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String ipen;

    /**
     * Gets the value of the accessionSpecimenNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccessionSpecimenNumbers() {
        return accessionSpecimenNumbers;
    }

    /**
     * Sets the value of the accessionSpecimenNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccessionSpecimenNumbers(Integer value) {
        this.accessionSpecimenNumbers = value;
    }

    /**
     * Gets the value of the accessionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getAccessionStatus() {
        return accessionStatus;
    }

    /**
     * Sets the value of the accessionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setAccessionStatus(Byte value) {
        this.accessionStatus = value;
    }

    /**
     * Gets the value of the locationInGarden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationInGarden() {
        return locationInGarden;
    }

    /**
     * Sets the value of the locationInGarden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationInGarden(String value) {
        this.locationInGarden = value;
    }

    /**
     * Gets the value of the accessionMaterialType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessionMaterialType() {
        return accessionMaterialType;
    }

    /**
     * Sets the value of the accessionMaterialType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessionMaterialType(String value) {
        this.accessionMaterialType = value;
    }

    /**
     * Gets the value of the hardiness property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHardiness() {
        return hardiness;
    }

    /**
     * Sets the value of the hardiness property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHardiness(Boolean value) {
        this.hardiness = value;
    }

    /**
     * Gets the value of the provenanceCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenanceCategory() {
        return provenanceCategory;
    }

    /**
     * Sets the value of the provenanceCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenanceCategory(String value) {
        this.provenanceCategory = value;
    }

    /**
     * Gets the value of the propagationHistoryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropagationHistoryCode() {
        return propagationHistoryCode;
    }

    /**
     * Sets the value of the propagationHistoryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropagationHistoryCode(String value) {
        this.propagationHistoryCode = value;
    }

    /**
     * Gets the value of the accessionLineage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessionLineage() {
        return accessionLineage;
    }

    /**
     * Sets the value of the accessionLineage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessionLineage(String value) {
        this.accessionLineage = value;
    }

    /**
     * Gets the value of the donorCategory property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getDonorCategory() {
        return donorCategory;
    }

    /**
     * Sets the value of the donorCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setDonorCategory(Byte value) {
        this.donorCategory = value;
    }

    /**
     * Gets the value of the cultivation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCultivation() {
        return cultivation;
    }

    /**
     * Sets the value of the cultivation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCultivation(String value) {
        this.cultivation = value;
    }

    /**
     * Gets the value of the plantingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlantingDate() {
        return plantingDate;
    }

    /**
     * Sets the value of the plantingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlantingDate(String value) {
        this.plantingDate = value;
    }

    /**
     * Gets the value of the propagation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropagation() {
        return propagation;
    }

    /**
     * Sets the value of the propagation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropagation(String value) {
        this.propagation = value;
    }

    /**
     * Gets the value of the perennation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerennation() {
        return perennation;
    }

    /**
     * Sets the value of the perennation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerennation(String value) {
        this.perennation = value;
    }

    /**
     * Gets the value of the breedingSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBreedingSystem() {
        return breedingSystem;
    }

    /**
     * Sets the value of the breedingSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBreedingSystem(String value) {
        this.breedingSystem = value;
    }

    /**
     * Gets the value of the ipen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPEN() {
        return ipen;
    }

    /**
     * Sets the value of the ipen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPEN(String value) {
        this.ipen = value;
    }

}
