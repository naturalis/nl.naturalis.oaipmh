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
 * An atomised name under the International Code of
 * 				Nomenclature of Bacteria
 * 			
 * 
 * <p>Java class for NameBacterial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NameBacterial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GenusOrMonomial" type="{http://www.tdwg.org/schemas/abcd/2.06}Monomial" minOccurs="0"/>
 *         &lt;element name="Subgenus" type="{http://www.tdwg.org/schemas/abcd/2.06}Monomial" minOccurs="0"/>
 *         &lt;element name="SubgenusAuthorAndYear" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="SpeciesEpithet" type="{http://www.tdwg.org/schemas/abcd/2.06}Epithet" minOccurs="0"/>
 *         &lt;element name="SubspeciesEpithet" type="{http://www.tdwg.org/schemas/abcd/2.06}Epithet" minOccurs="0"/>
 *         &lt;element name="ParentheticalAuthorTeamAndYear" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="AuthorTeamAndYear" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="NameApprobation" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameBacterial", propOrder = {
    "genusOrMonomial",
    "subgenus",
    "subgenusAuthorAndYear",
    "speciesEpithet",
    "subspeciesEpithet",
    "parentheticalAuthorTeamAndYear",
    "authorTeamAndYear",
    "nameApprobation"
})
public class NameBacterial {

    @XmlElement(name = "GenusOrMonomial")
    protected String genusOrMonomial;
    @XmlElement(name = "Subgenus")
    protected String subgenus;
    @XmlElement(name = "SubgenusAuthorAndYear")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String subgenusAuthorAndYear;
    @XmlElement(name = "SpeciesEpithet")
    protected String speciesEpithet;
    @XmlElement(name = "SubspeciesEpithet")
    protected String subspeciesEpithet;
    @XmlElement(name = "ParentheticalAuthorTeamAndYear")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String parentheticalAuthorTeamAndYear;
    @XmlElement(name = "AuthorTeamAndYear")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String authorTeamAndYear;
    @XmlElement(name = "NameApprobation")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String nameApprobation;

    /**
     * Gets the value of the genusOrMonomial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenusOrMonomial() {
        return genusOrMonomial;
    }

    /**
     * Sets the value of the genusOrMonomial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenusOrMonomial(String value) {
        this.genusOrMonomial = value;
    }

    /**
     * Gets the value of the subgenus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubgenus() {
        return subgenus;
    }

    /**
     * Sets the value of the subgenus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubgenus(String value) {
        this.subgenus = value;
    }

    /**
     * Gets the value of the subgenusAuthorAndYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubgenusAuthorAndYear() {
        return subgenusAuthorAndYear;
    }

    /**
     * Sets the value of the subgenusAuthorAndYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubgenusAuthorAndYear(String value) {
        this.subgenusAuthorAndYear = value;
    }

    /**
     * Gets the value of the speciesEpithet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeciesEpithet() {
        return speciesEpithet;
    }

    /**
     * Sets the value of the speciesEpithet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeciesEpithet(String value) {
        this.speciesEpithet = value;
    }

    /**
     * Gets the value of the subspeciesEpithet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubspeciesEpithet() {
        return subspeciesEpithet;
    }

    /**
     * Sets the value of the subspeciesEpithet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubspeciesEpithet(String value) {
        this.subspeciesEpithet = value;
    }

    /**
     * Gets the value of the parentheticalAuthorTeamAndYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentheticalAuthorTeamAndYear() {
        return parentheticalAuthorTeamAndYear;
    }

    /**
     * Sets the value of the parentheticalAuthorTeamAndYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentheticalAuthorTeamAndYear(String value) {
        this.parentheticalAuthorTeamAndYear = value;
    }

    /**
     * Gets the value of the authorTeamAndYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorTeamAndYear() {
        return authorTeamAndYear;
    }

    /**
     * Sets the value of the authorTeamAndYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorTeamAndYear(String value) {
        this.authorTeamAndYear = value;
    }

    /**
     * Gets the value of the nameApprobation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameApprobation() {
        return nameApprobation;
    }

    /**
     * Sets the value of the nameApprobation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameApprobation(String value) {
        this.nameApprobation = value;
    }

}
