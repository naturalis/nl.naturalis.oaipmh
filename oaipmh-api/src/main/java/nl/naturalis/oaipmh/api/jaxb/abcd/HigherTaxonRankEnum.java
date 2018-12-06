//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.16 at 11:51:40 AM CET 
//


package nl.naturalis.oaipmh.api.jaxb.abcd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HigherTaxonRankEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HigherTaxonRankEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}Name">
 *     &lt;enumeration value="regnum"/>
 *     &lt;enumeration value="subregnum"/>
 *     &lt;enumeration value="superphylum"/>
 *     &lt;enumeration value="phylum"/>
 *     &lt;enumeration value="subphylum"/>
 *     &lt;enumeration value="superclassis"/>
 *     &lt;enumeration value="classis"/>
 *     &lt;enumeration value="subclassis"/>
 *     &lt;enumeration value="superordo"/>
 *     &lt;enumeration value="ordo"/>
 *     &lt;enumeration value="subordo"/>
 *     &lt;enumeration value="superfamilia"/>
 *     &lt;enumeration value="familia"/>
 *     &lt;enumeration value="subfamilia"/>
 *     &lt;enumeration value="tribus"/>
 *     &lt;enumeration value="genusgroup"/>
 *     &lt;enumeration value="unranked"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HigherTaxonRankEnum")
@XmlEnum
public enum HigherTaxonRankEnum {

    @XmlEnumValue("regnum")
    REGNUM("regnum"),
    @XmlEnumValue("subregnum")
    SUBREGNUM("subregnum"),
    @XmlEnumValue("superphylum")
    SUPERPHYLUM("superphylum"),
    @XmlEnumValue("phylum")
    PHYLUM("phylum"),
    @XmlEnumValue("subphylum")
    SUBPHYLUM("subphylum"),
    @XmlEnumValue("superclassis")
    SUPERCLASSIS("superclassis"),
    @XmlEnumValue("classis")
    CLASSIS("classis"),
    @XmlEnumValue("subclassis")
    SUBCLASSIS("subclassis"),
    @XmlEnumValue("superordo")
    SUPERORDO("superordo"),
    @XmlEnumValue("ordo")
    ORDO("ordo"),
    @XmlEnumValue("subordo")
    SUBORDO("subordo"),
    @XmlEnumValue("superfamilia")
    SUPERFAMILIA("superfamilia"),
    @XmlEnumValue("familia")
    FAMILIA("familia"),
    @XmlEnumValue("subfamilia")
    SUBFAMILIA("subfamilia"),
    @XmlEnumValue("tribus")
    TRIBUS("tribus"),
    @XmlEnumValue("genusgroup")
    GENUSGROUP("genusgroup"),
    @XmlEnumValue("unranked")
    UNRANKED("unranked");
    private final String value;

    HigherTaxonRankEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HigherTaxonRankEnum fromValue(String v) {
        for (HigherTaxonRankEnum c: HigherTaxonRankEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}