//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:51:40 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.abcd;

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
 * Name of the taxon identified, formed according to
 * 				the different Codes of Nomenclature which apply to scientific names
 * 				- with additional elements for suffixes or other expressions
 * 				commonly used in taxonomic identifications.
 * 			
 * 
 * <p>Java class for ScientificNameIdentified complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScientificNameIdentified">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tdwg.org/schemas/abcd/2.06}ScientificName">
 *       &lt;sequence>
 *         &lt;element name="IdentificationQualifier" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="insertionpoint">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *                       &lt;enumeration value="1"/>
 *                       &lt;enumeration value="2"/>
 *                       &lt;enumeration value="3"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="NameAddendum" type="{http://www.tdwg.org/schemas/abcd/2.06}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScientificNameIdentified", propOrder = {
    "identificationQualifier",
    "nameAddendum"
})
public class ScientificNameIdentified
    extends ScientificName
{

    @XmlElement(name = "IdentificationQualifier")
    protected ScientificNameIdentified.IdentificationQualifier identificationQualifier;
    @XmlElement(name = "NameAddendum")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String nameAddendum;

    /**
     * Gets the value of the identificationQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link ScientificNameIdentified.IdentificationQualifier }
     *     
     */
    public ScientificNameIdentified.IdentificationQualifier getIdentificationQualifier() {
        return identificationQualifier;
    }

    /**
     * Sets the value of the identificationQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScientificNameIdentified.IdentificationQualifier }
     *     
     */
    public void setIdentificationQualifier(ScientificNameIdentified.IdentificationQualifier value) {
        this.identificationQualifier = value;
    }

    /**
     * Gets the value of the nameAddendum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameAddendum() {
        return nameAddendum;
    }

    /**
     * Sets the value of the nameAddendum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameAddendum(String value) {
        this.nameAddendum = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="insertionpoint">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
     *             &lt;enumeration value="1"/>
     *             &lt;enumeration value="2"/>
     *             &lt;enumeration value="3"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
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
    public static class IdentificationQualifier {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "insertionpoint")
        protected Integer insertionpoint;

        /**
         * Gets the value of the value property.
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

}
