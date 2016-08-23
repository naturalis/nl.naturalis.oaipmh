//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:51:40 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.abcd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * An atomised scientific name under the International
 * 				Code of Botanical Nomenclature or the International Code of
 * 				Nomenclature for Cultivated Plants
 * 			
 * 
 * <p>Java class for NameBotanical complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NameBotanical">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GenusOrMonomial" type="{http://www.tdwg.org/schemas/abcd/2.06}Monomial" minOccurs="0"/>
 *         &lt;element name="FirstEpithet" type="{http://www.tdwg.org/schemas/abcd/2.06}Epithet" minOccurs="0"/>
 *         &lt;element name="InfraspecificEpithet" type="{http://www.tdwg.org/schemas/abcd/2.06}Epithet" minOccurs="0"/>
 *         &lt;element name="Rank" type="{http://www.tdwg.org/schemas/abcd/2.06}RankAbbreviation" minOccurs="0"/>
 *         &lt;element name="HybridFlag" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.tdwg.org/schemas/abcd/2.06>HybridFlag">
 *                 &lt;attribute name="insertionpoint" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AuthorTeamParenthesis" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="AuthorTeam" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="CultivarGroupName" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="CultivarName" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *         &lt;element name="TradeDesignationNames" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TradeDesignationName" type="{http://www.tdwg.org/schemas/abcd/2.06}String" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "NameBotanical", propOrder = {
    "genusOrMonomial",
    "firstEpithet",
    "infraspecificEpithet",
    "rank",
    "hybridFlag",
    "authorTeamParenthesis",
    "authorTeam",
    "cultivarGroupName",
    "cultivarName",
    "tradeDesignationNames"
})
public class NameBotanical {

    @XmlElement(name = "GenusOrMonomial")
    protected String genusOrMonomial;
    @XmlElement(name = "FirstEpithet")
    protected String firstEpithet;
    @XmlElement(name = "InfraspecificEpithet")
    protected String infraspecificEpithet;
    @XmlElement(name = "Rank")
    @XmlSchemaType(name = "Name")
    protected RankAbbreviation rank;
    @XmlElement(name = "HybridFlag")
    protected NameBotanical.HybridFlag hybridFlag;
    @XmlElement(name = "AuthorTeamParenthesis")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String authorTeamParenthesis;
    @XmlElement(name = "AuthorTeam")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String authorTeam;
    @XmlElement(name = "CultivarGroupName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cultivarGroupName;
    @XmlElement(name = "CultivarName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cultivarName;
    @XmlElement(name = "TradeDesignationNames")
    protected NameBotanical.TradeDesignationNames tradeDesignationNames;

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
     * Gets the value of the firstEpithet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstEpithet() {
        return firstEpithet;
    }

    /**
     * Sets the value of the firstEpithet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstEpithet(String value) {
        this.firstEpithet = value;
    }

    /**
     * Gets the value of the infraspecificEpithet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfraspecificEpithet() {
        return infraspecificEpithet;
    }

    /**
     * Sets the value of the infraspecificEpithet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfraspecificEpithet(String value) {
        this.infraspecificEpithet = value;
    }

    /**
     * Gets the value of the rank property.
     * 
     * @return
     *     possible object is
     *     {@link RankAbbreviation }
     *     
     */
    public RankAbbreviation getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     * 
     * @param value
     *     allowed object is
     *     {@link RankAbbreviation }
     *     
     */
    public void setRank(RankAbbreviation value) {
        this.rank = value;
    }

    /**
     * Gets the value of the hybridFlag property.
     * 
     * @return
     *     possible object is
     *     {@link NameBotanical.HybridFlag }
     *     
     */
    public NameBotanical.HybridFlag getHybridFlag() {
        return hybridFlag;
    }

    /**
     * Sets the value of the hybridFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameBotanical.HybridFlag }
     *     
     */
    public void setHybridFlag(NameBotanical.HybridFlag value) {
        this.hybridFlag = value;
    }

    /**
     * Gets the value of the authorTeamParenthesis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorTeamParenthesis() {
        return authorTeamParenthesis;
    }

    /**
     * Sets the value of the authorTeamParenthesis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorTeamParenthesis(String value) {
        this.authorTeamParenthesis = value;
    }

    /**
     * Gets the value of the authorTeam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorTeam() {
        return authorTeam;
    }

    /**
     * Sets the value of the authorTeam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorTeam(String value) {
        this.authorTeam = value;
    }

    /**
     * Gets the value of the cultivarGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCultivarGroupName() {
        return cultivarGroupName;
    }

    /**
     * Sets the value of the cultivarGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCultivarGroupName(String value) {
        this.cultivarGroupName = value;
    }

    /**
     * Gets the value of the cultivarName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCultivarName() {
        return cultivarName;
    }

    /**
     * Sets the value of the cultivarName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCultivarName(String value) {
        this.cultivarName = value;
    }

    /**
     * Gets the value of the tradeDesignationNames property.
     * 
     * @return
     *     possible object is
     *     {@link NameBotanical.TradeDesignationNames }
     *     
     */
    public NameBotanical.TradeDesignationNames getTradeDesignationNames() {
        return tradeDesignationNames;
    }

    /**
     * Sets the value of the tradeDesignationNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameBotanical.TradeDesignationNames }
     *     
     */
    public void setTradeDesignationNames(NameBotanical.TradeDesignationNames value) {
        this.tradeDesignationNames = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.tdwg.org/schemas/abcd/2.06>HybridFlag">
     *       &lt;attribute name="insertionpoint" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class HybridFlag {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "insertionpoint")
        protected Integer insertionpoint;

        /**
         * A multiplication or plus sign designating a hybrid
         * 				or draft chimaera in botany.
         * 			
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the insertionpoint property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getInsertionpoint() {
            return insertionpoint;
        }

        /**
         * Sets the value of the insertionpoint property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setInsertionpoint(Integer value) {
            this.insertionpoint = value;
        }

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
     *         &lt;element name="TradeDesignationName" type="{http://www.tdwg.org/schemas/abcd/2.06}String" maxOccurs="unbounded" minOccurs="0"/>
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
        "tradeDesignationName"
    })
    public static class TradeDesignationNames {

        @XmlElement(name = "TradeDesignationName")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected List<String> tradeDesignationName;

        /**
         * Gets the value of the tradeDesignationName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tradeDesignationName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTradeDesignationName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTradeDesignationName() {
            if (tradeDesignationName == null) {
                tradeDesignationName = new ArrayList<String>();
            }
            return this.tradeDesignationName;
        }

    }

}