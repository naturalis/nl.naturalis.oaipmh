package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.CANNOT_DISSEMINATE_FORMAT;

import org.openarchives.oai._2.OAIPMHerrorType;

/**
 * Convenience class narrowing the JAXB {@code OAIPMHerrorType} class to one for CannotDisseminateFormat errors.
 * 
 * @author Ayco Holleman
 *
 */
public class CannotDisseminateFormatError extends OAIPMHerrorType {

  public CannotDisseminateFormatError(String metadataPrefix) {
    super();
    this.code = CANNOT_DISSEMINATE_FORMAT;
    this.value = "Cannot disseminate format: " + metadataPrefix;
  }

  public CannotDisseminateFormatError(OAIPMHRequest request) {
    this(request.getMetadataPrefix());
  }

}
