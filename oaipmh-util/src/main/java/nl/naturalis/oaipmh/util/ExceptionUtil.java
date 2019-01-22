package nl.naturalis.oaipmh.util;

import java.util.function.Function;

public class ExceptionUtil {

  /**
   * Wraps the provided Throwable into a RuntimeException if it isn't one yet, else simply returns the Throwable.
   * 
   * @param t
   * @return
   */
  public static RuntimeException smash(Throwable t) {
    if (t instanceof RuntimeException) {
      return (RuntimeException) t;
    }
    return new RuntimeException(t);
  }

  public static RuntimeException smash(Throwable t, Function<Throwable, RuntimeException> f) {
    if (t instanceof RuntimeException) {
      return (RuntimeException) t;
    }
    return f.apply(t);
  }

  /**
   * Returns the root cause of the specified throwable.
   * 
   * @param t
   * @return
   */
  public static Throwable getRootCause(Throwable t) {
    while (t.getCause() != null) {
      t = t.getCause();
    }
    return t;
  }

  /**
   * Returns the stack trace of the root cause of {@code t} as a string.
   * 
   * @param t
   * @return
   */
  public static String getRootCauseStackTrace(Throwable t) {
    t = getRootCause(t);
    StringBuilder sb = new StringBuilder(6000);
    sb.append(t.toString());
    for (StackTraceElement e : t.getStackTrace()) {
      sb.append("\nat ");
      sb.append(e.getClassName()).append('.').append(e.getMethodName());
      sb.append('(').append(e.getFileName());
      sb.append(':').append(e.getLineNumber()).append(')');
    }
    return sb.toString();
  }

}
