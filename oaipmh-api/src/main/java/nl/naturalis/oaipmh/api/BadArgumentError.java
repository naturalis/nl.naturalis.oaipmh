package nl.naturalis.oaipmh.api;

import static org.openarchives.oai._2.OAIPMHerrorcodeType.BAD_ARGUMENT;

import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateFormat;
import static nl.naturalis.oaipmh.api.util.OAIPMHUtil.dateTimeFormat;

import java.util.Set;
import java.util.stream.Collectors;

import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.VerbType;

/**
 * Convenience class narrowing the JAXB {@code OAIPMHerrorType} class to one for BadArgument errors.
 * 
 * @author Ayco Holleman
 *
 */
public class BadArgumentError extends OAIPMHerrorType {

  public static BadArgumentError verbNotSupported(VerbType notSupported, Set<VerbType> supported) {
    StringBuilder sb = new StringBuilder(100)
        .append("Verb not supported: ")
        .append(notSupported.value())
        .append(". Supported: ")
        .append(supported.stream().map(VerbType::value).collect(Collectors.joining(", ")));
    return new BadArgumentError(sb.toString());
  }

  public static BadArgumentError badDate(String param) {
    String fmt = "Invalid date: \"%s\" (Must be either \"%s\" or \"%s\")";
    String msg = String.format(fmt, param, dateTimeFormat, dateFormat);
    return new BadArgumentError(msg);
  }

  public BadArgumentError(String message) {
    super();
    this.code = BAD_ARGUMENT;
    this.value = message;
  }

}
