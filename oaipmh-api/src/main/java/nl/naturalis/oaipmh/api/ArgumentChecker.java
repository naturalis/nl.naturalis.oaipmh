package nl.naturalis.oaipmh.api;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openarchives.oai._2.OAIPMHerrorType;
import org.openarchives.oai._2.VerbType;

/**
 * Abstract base class for classes that check the validity of the {@link Argument arguments} passed allong with a certain {@link VerbType
 * verb} . For each verb has there is a subclass of this base class. An {@code ArgumentChecker} does <i>not</i> check the value of the
 * arguments. It only checks that all required arguments are present, forbidden arguments are absent, and mutually exclusive arguments are
 * not both present.
 * 
 * @author Ayco Holleman
 *
 */
public abstract class ArgumentChecker {

  @SuppressWarnings("unused")
  private static final Logger logger = LogManager.getLogger(ArgumentChecker.class);

  public ArgumentChecker() {}

  /**
   * Checks the validity of the provided arguments. The {@link Argument#VERB verb} itself must not be present in the provided set of
   * arguments.
   * 
   * @param arguments
   * @return
   */
  public List<OAIPMHerrorType> check(EnumSet<Argument> arguments) {
    List<OAIPMHerrorType> errors = new ArrayList<>(4);
    if (!beforeCheck(arguments, errors)) {
      return errors;
    }
    EnumSet<Argument> required = EnumSet.copyOf(getRequiredArguments());
    required.removeAll(arguments);
    if (required.size() != 0) {
      String missing = required.stream().map(String::valueOf).collect(Collectors.joining(","));
      String s = required.size() == 1 ? "" : "s";
      String fmt = "Missing required argument%s: %s";
      String msg = String.format(fmt, s, missing);
      errors.add(new BadArgumentError(msg));
    }
    EnumSet<Argument> copy = EnumSet.copyOf(arguments);
    copy.removeAll(getRequiredArguments());
    copy.removeAll(getOptionalArguments());
    if (copy.size() != 0) {
      String illegal = copy.stream().map(String::valueOf).collect(Collectors.joining(","));
      String s = copy.size() == 1 ? "" : "s";
      String fmt = "Illegal argument%s: %s";
      String msg = String.format(fmt, s, illegal);
      errors.add(new BadArgumentError(msg));
    }
    return errors;
  }

  /**
   * Hook for subclasses to expand and control the argument checking process. Called at the beginning of the {@link #check(EnumSet) check}
   * method. If the {@code beforeCheck} method returns {@code false}, no further checks are done and the {@code check} method returns
   * immediately.
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
