package nl.naturalis.oaipmh.geneious;

import java.util.EnumMap;
import java.util.Objects;

/**
 * A {@code DocumentHiddenFields} instance maintains the values of all possibly relevant elements under the &lt;hiddenFields&gt;
 * element within the document_xml column.
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentHiddenFields {

  /**
   * Enumerates all XML element tag names underneath the &lt;fields&gt; element of the document_xml column that might be used for
   * OAI-PMH generation.
   */
  public static enum HiddenField {
    description, cache_name, override_cache_name
  }

  private EnumMap<HiddenField, String> data = new EnumMap<>(HiddenField.class);

  /**
   * Returns the number of notes extracted from the XML in the document_xml column.
   * 
   * @return
   */
  public int count() {
    return data.size();
  }

  /**
   * Whether or not the document_xml column contains the specified &lt;note&gt; element.
   * 
   * @param hiddenField
   * @return
   */
  public boolean isSet(HiddenField hiddenField) {
    return data.containsKey(hiddenField);
  }

  /**
   * Set the value of the specified note.
   * 
   * @param hiddenField
   * @param value
   */
  public void set(HiddenField hiddenField, String value) {
    data.put(hiddenField, value);
  }

  /**
   * Get the value of the specified note.
   * 
   * @param hiddenField
   * @return
   */
  public String get(HiddenField hiddenField) {
    return data.get(hiddenField);
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DocumentHiddenFields other = (DocumentHiddenFields) obj;
    return Objects.equals(data, other.data);
  }

  public int hashCode() {
    return Objects.hash(data);
  }

}
