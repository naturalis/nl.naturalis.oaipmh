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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Metadata referring to the
 * 				principal source of the entire data collection (thus the metadata
 * 				scope may be wider than the objects actually contained in the data
 * 				set). If a history of the data collection (revised or expanded in
 * 				various projects or at different institutions) exist, this must be
 * 				reflected in the IPR statements and possibly in the list of Owners.
 * 			
 * 
 * <p>Java class for ContentMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContentMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Description">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Representation" type="{http://www.tdwg.org/schemas/abcd/2.06}MetadataDescriptionRepr" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IconURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="Scope" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="GeoecologicalTerms" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="GeoEcologicalTerm" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL255" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="TaxonomicTerms" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TaxonomicTerm" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL255" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Version" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Major" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *                   &lt;element name="Minor" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *                   &lt;element name="Modifier" type="{http://www.tdwg.org/schemas/abcd/2.06}String255" minOccurs="0"/>
 *                   &lt;element name="DateIssued" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RevisionData" type="{http://www.tdwg.org/schemas/abcd/2.06}RevisionData"/>
 *         &lt;element name="Owners" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Owner" type="{http://www.tdwg.org/schemas/abcd/2.06}Contact" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IPRStatements" type="{http://www.tdwg.org/schemas/abcd/2.06}IPRStatements" minOccurs="0"/>
 *         &lt;element name="InformationWithheld" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL" minOccurs="0"/>
 *         &lt;element name="DirectAccessURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContentMetadata", propOrder = {
    "description",
    "iconURI",
    "scope",
    "version",
    "revisionData",
    "owners",
    "iprStatements",
    "informationWithheld",
    "directAccessURI"
})
public class ContentMetadata {

    @XmlElement(name = "Description", required = true)
    protected ContentMetadata.Description description;
    @XmlElement(name = "IconURI")
    @XmlSchemaType(name = "anyURI")
    protected String iconURI;
    @XmlElement(name = "Scope")
    protected ContentMetadata.Scope scope;
    @XmlElement(name = "Version")
    protected ContentMetadata.Version version;
    @XmlElement(name = "RevisionData", required = true)
    protected RevisionData revisionData;
    @XmlElement(name = "Owners")
    protected ContentMetadata.Owners owners;
    @XmlElement(name = "IPRStatements")
    protected IPRStatements iprStatements;
    @XmlElement(name = "InformationWithheld")
    protected StringL informationWithheld;
    @XmlElement(name = "DirectAccessURI")
    @XmlSchemaType(name = "anyURI")
    protected String directAccessURI;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link ContentMetadata.Description }
     *     
     */
    public ContentMetadata.Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentMetadata.Description }
     *     
     */
    public void setDescription(ContentMetadata.Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the iconURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIconURI() {
        return iconURI;
    }

    /**
     * Sets the value of the iconURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIconURI(String value) {
        this.iconURI = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link ContentMetadata.Scope }
     *     
     */
    public ContentMetadata.Scope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentMetadata.Scope }
     *     
     */
    public void setScope(ContentMetadata.Scope value) {
        this.scope = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link ContentMetadata.Version }
     *     
     */
    public ContentMetadata.Version getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentMetadata.Version }
     *     
     */
    public void setVersion(ContentMetadata.Version value) {
        this.version = value;
    }

    /**
     * Gets the value of the revisionData property.
     * 
     * @return
     *     possible object is
     *     {@link RevisionData }
     *     
     */
    public RevisionData getRevisionData() {
        return revisionData;
    }

    /**
     * Sets the value of the revisionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RevisionData }
     *     
     */
    public void setRevisionData(RevisionData value) {
        this.revisionData = value;
    }

    /**
     * Gets the value of the owners property.
     * 
     * @return
     *     possible object is
     *     {@link ContentMetadata.Owners }
     *     
     */
    public ContentMetadata.Owners getOwners() {
        return owners;
    }

    /**
     * Sets the value of the owners property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentMetadata.Owners }
     *     
     */
    public void setOwners(ContentMetadata.Owners value) {
        this.owners = value;
    }

    /**
     * Gets the value of the iprStatements property.
     * 
     * @return
     *     possible object is
     *     {@link IPRStatements }
     *     
     */
    public IPRStatements getIPRStatements() {
        return iprStatements;
    }

    /**
     * Sets the value of the iprStatements property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPRStatements }
     *     
     */
    public void setIPRStatements(IPRStatements value) {
        this.iprStatements = value;
    }

    /**
     * Gets the value of the informationWithheld property.
     * 
     * @return
     *     possible object is
     *     {@link StringL }
     *     
     */
    public StringL getInformationWithheld() {
        return informationWithheld;
    }

    /**
     * Sets the value of the informationWithheld property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringL }
     *     
     */
    public void setInformationWithheld(StringL value) {
        this.informationWithheld = value;
    }

    /**
     * Gets the value of the directAccessURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectAccessURI() {
        return directAccessURI;
    }

    /**
     * Sets the value of the directAccessURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectAccessURI(String value) {
        this.directAccessURI = value;
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
     *         &lt;element name="Representation" type="{http://www.tdwg.org/schemas/abcd/2.06}MetadataDescriptionRepr" maxOccurs="unbounded"/>
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
        "representation"
    })
    public static class Description {

        @XmlElement(name = "Representation", required = true)
        protected List<MetadataDescriptionRepr> representation;

        /**
         * Gets the value of the representation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the representation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRepresentation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MetadataDescriptionRepr }
         * 
         * 
         */
        public List<MetadataDescriptionRepr> getRepresentation() {
            if (representation == null) {
                representation = new ArrayList<MetadataDescriptionRepr>();
            }
            return this.representation;
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
     *         &lt;element name="Owner" type="{http://www.tdwg.org/schemas/abcd/2.06}Contact" maxOccurs="unbounded" minOccurs="0"/>
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
        "owner"
    })
    public static class Owners {

        @XmlElement(name = "Owner")
        protected List<Contact> owner;

        /**
         * Gets the value of the owner property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the owner property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOwner().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Contact }
         * 
         * 
         */
        public List<Contact> getOwner() {
            if (owner == null) {
                owner = new ArrayList<Contact>();
            }
            return this.owner;
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
     *         &lt;element name="GeoecologicalTerms" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="GeoEcologicalTerm" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL255" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="TaxonomicTerms" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TaxonomicTerm" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL255" maxOccurs="unbounded"/>
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
    @XmlType(name = "", propOrder = {
        "geoecologicalTerms",
        "taxonomicTerms"
    })
    public static class Scope {

        @XmlElement(name = "GeoecologicalTerms")
        protected ContentMetadata.Scope.GeoecologicalTerms geoecologicalTerms;
        @XmlElement(name = "TaxonomicTerms")
        protected ContentMetadata.Scope.TaxonomicTerms taxonomicTerms;

        /**
         * Gets the value of the geoecologicalTerms property.
         * 
         * @return
         *     possible object is
         *     {@link ContentMetadata.Scope.GeoecologicalTerms }
         *     
         */
        public ContentMetadata.Scope.GeoecologicalTerms getGeoecologicalTerms() {
            return geoecologicalTerms;
        }

        /**
         * Sets the value of the geoecologicalTerms property.
         * 
         * @param value
         *     allowed object is
         *     {@link ContentMetadata.Scope.GeoecologicalTerms }
         *     
         */
        public void setGeoecologicalTerms(ContentMetadata.Scope.GeoecologicalTerms value) {
            this.geoecologicalTerms = value;
        }

        /**
         * Gets the value of the taxonomicTerms property.
         * 
         * @return
         *     possible object is
         *     {@link ContentMetadata.Scope.TaxonomicTerms }
         *     
         */
        public ContentMetadata.Scope.TaxonomicTerms getTaxonomicTerms() {
            return taxonomicTerms;
        }

        /**
         * Sets the value of the taxonomicTerms property.
         * 
         * @param value
         *     allowed object is
         *     {@link ContentMetadata.Scope.TaxonomicTerms }
         *     
         */
        public void setTaxonomicTerms(ContentMetadata.Scope.TaxonomicTerms value) {
            this.taxonomicTerms = value;
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
         *         &lt;element name="GeoEcologicalTerm" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL255" maxOccurs="unbounded"/>
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
            "geoEcologicalTerm"
        })
        public static class GeoecologicalTerms {

            @XmlElement(name = "GeoEcologicalTerm", required = true)
            protected List<StringL255> geoEcologicalTerm;

            /**
             * Gets the value of the geoEcologicalTerm property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the geoEcologicalTerm property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGeoEcologicalTerm().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link StringL255 }
             * 
             * 
             */
            public List<StringL255> getGeoEcologicalTerm() {
                if (geoEcologicalTerm == null) {
                    geoEcologicalTerm = new ArrayList<StringL255>();
                }
                return this.geoEcologicalTerm;
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
         *         &lt;element name="TaxonomicTerm" type="{http://www.tdwg.org/schemas/abcd/2.06}StringL255" maxOccurs="unbounded"/>
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
            "taxonomicTerm"
        })
        public static class TaxonomicTerms {

            @XmlElement(name = "TaxonomicTerm", required = true)
            protected List<StringL255> taxonomicTerm;

            /**
             * Gets the value of the taxonomicTerm property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the taxonomicTerm property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTaxonomicTerm().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link StringL255 }
             * 
             * 
             */
            public List<StringL255> getTaxonomicTerm() {
                if (taxonomicTerm == null) {
                    taxonomicTerm = new ArrayList<StringL255>();
                }
                return this.taxonomicTerm;
            }

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
     *         &lt;element name="Major" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
     *         &lt;element name="Minor" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
     *         &lt;element name="Modifier" type="{http://www.tdwg.org/schemas/abcd/2.06}String255" minOccurs="0"/>
     *         &lt;element name="DateIssued" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
        "major",
        "minor",
        "modifier",
        "dateIssued"
    })
    public static class Version {

        @XmlElement(name = "Major", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger major;
        @XmlElement(name = "Minor")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger minor;
        @XmlElement(name = "Modifier")
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String modifier;
        @XmlElement(name = "DateIssued")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dateIssued;

        /**
         * Gets the value of the major property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMajor() {
            return major;
        }

        /**
         * Sets the value of the major property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMajor(BigInteger value) {
            this.major = value;
        }

        /**
         * Gets the value of the minor property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMinor() {
            return minor;
        }

        /**
         * Sets the value of the minor property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMinor(BigInteger value) {
            this.minor = value;
        }

        /**
         * Gets the value of the modifier property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getModifier() {
            return modifier;
        }

        /**
         * Sets the value of the modifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setModifier(String value) {
            this.modifier = value;
        }

        /**
         * Gets the value of the dateIssued property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDateIssued() {
            return dateIssued;
        }

        /**
         * Sets the value of the dateIssued property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDateIssued(XMLGregorianCalendar value) {
            this.dateIssued = value;
        }

    }

}
