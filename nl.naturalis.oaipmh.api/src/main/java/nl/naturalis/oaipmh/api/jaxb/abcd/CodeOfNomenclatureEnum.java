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
 * <p>Java class for CodeOfNomenclatureEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CodeOfNomenclatureEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}Name">
 *     &lt;enumeration value="Bacteriological"/>
 *     &lt;enumeration value="Botanical"/>
 *     &lt;enumeration value="Viral"/>
 *     &lt;enumeration value="Zoological"/>
 *     &lt;enumeration value="Cultivated"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CodeOfNomenclatureEnum")
@XmlEnum
public enum CodeOfNomenclatureEnum {

    @XmlEnumValue("Bacteriological")
    BACTERIOLOGICAL("Bacteriological"),
    @XmlEnumValue("Botanical")
    BOTANICAL("Botanical"),
    @XmlEnumValue("Viral")
    VIRAL("Viral"),
    @XmlEnumValue("Zoological")
    ZOOLOGICAL("Zoological"),
    @XmlEnumValue("Cultivated")
    CULTIVATED("Cultivated");
    private final String value;

    CodeOfNomenclatureEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CodeOfNomenclatureEnum fromValue(String v) {
        for (CodeOfNomenclatureEnum c: CodeOfNomenclatureEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
