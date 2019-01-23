package nl.naturalis.oaipmh.api;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.VerbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for classes that check the validity of the {@link Argument arguments} passed along with a {@link VerbType
 * verb} (like ListRecords). For each verb has there is a subclass of this base class. An {@code ArgumentChecker} does <i>not</i>
 * check the value of the arguments. It only checks that all required arguments are present, forbidden arguments are absent, and
 * mutually exclusive arguments are not both present.
 * 
 * @author Ayco Holleman
 *
 */
public abstract class ArgumentValidator {

  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(ArgumentValidator.class);

  public ArgumentValidator() {}

  /**
   * Checks the validity of the provided arguments. The {@link Argument#VERB verb} itself must <i>not</i> be present in the provided
   * set of arguments.
   * 
   * @param arguments
   * @return
   */
  public List<OAIPMHerrorType> validate(EnumSet<Argument> arguments) {
    List<OAIPMHerrorType> errors = new ArrayList<>(4);
    if (!beforeCheck(arguments, errors)) {
      return errors;
    }
    EnumSet<Argument> required = EnumSet.copyOf(getRequiredArguments());
    required.removeAll(arguments);
    if (required.size() != 0) {
      String missing = required.stream().map(Argument::param).collect(Collectors.joining(","));
      String s = required.size() == 1 ? "" : "s";
      String msg = String.format("Missing required argument%s: %s", s, missing);
      errors.add(new BadArgumentError(msg));
    }
    EnumSet<Argument> args = EnumSet.copyOf(arguments);
    args.removeAll(getRequiredArguments());
    args.removeAll(getOptionalArguments());
    if (args.size() != 0) {
      String illegalArgs = args.stream().map(Argument::param).collect(Collectors.joining(","));
      String s = args.size() == 1 ? "" : "s";
      String msg = String.format("Illegal argument%s: %s", s, illegalArgs);
      errors.add(new BadArgumentError(msg));
    }
    return errors;
  }

  /**
   * Hook for subclasses to expand and control the argument checking process. Called at the beginning of the
   * {@link #validate(EnumSet) check} method. If the {@code beforeCheck} method returns {@code false}, no further checks are done
   * and the {@code check} method returns immediately.
   * 
   * @param arguments
   * @param errors
   * @return
   */
  protected boolean beforeCheck(EnumSet<Argument> arguments, List<OAIPMHerrorType> errors) {
    return true;
  }

  /**
   * To be implemented by subclasses: get the required arguments for the {@link VerbType verb} that the subclass deals with.
   * 
   * @return
   */
  protected abstract EnumSet<Argument> getRequiredArguments();

  /**
   * To be implemented by subclasses: get the optional arguments for the {@link VerbType verb} that the subclass deals with.
   * 
   * @return
   */
  protected abstract EnumSet<Argument> getOptionalArguments();

}
