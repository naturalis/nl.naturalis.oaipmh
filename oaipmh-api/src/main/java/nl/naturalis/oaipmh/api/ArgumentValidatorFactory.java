package nl.naturalis.oaipmh.api;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;

import org.openarchives.oai._2.VerbType;

import static org.openarchives.oai._2.VerbType.LIST_RECORDS;

/**
 * Creates {@link ArgumentValidator} instances.
 * 
 * @author Ayco Holleman
 *
 */
public class ArgumentValidatorFactory {

  public static final ArgumentValidatorFactory INSTANCE = new ArgumentValidatorFactory();

  private final EnumMap<VerbType, ArgumentValidator> validators;

  private ArgumentValidatorFactory() {
    validators = new EnumMap<>(VerbType.class);
    validators.put(LIST_RECORDS, new ListRecordsArgumentChecker());
  }

  /**
   * Creates an {@link ArgumentValidator} instance for the specified verb.
   * 
   * @param verb
   * @return
   */
  public Optional<ArgumentValidator> getValidatorForVerb(VerbType verb) {
    if (validators.containsKey(verb)) {
      return Optional.of(validators.get(verb));
    }
    return Optional.empty();
  }

  /**
   * Returns all OAI-PMH verbs currently supported by the OAI-PMH service.
   * @return
   */
  public Set<VerbType> getSupportedVerbs() {
    return validators.keySet();
  }

}
