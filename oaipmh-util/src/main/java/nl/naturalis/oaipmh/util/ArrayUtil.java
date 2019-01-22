package nl.naturalis.oaipmh.util;

import java.util.ArrayList;
import java.util.Enumeration;

public class ArrayUtil {
  private ArrayUtil() {}

  /**
   * Convert an {@code Enumeration} to an array.
   * 
   * @param e
   *          The {@code Enumeration}
   * @return The array
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] toArray(Enumeration<T> e) {
    ArrayList<T> list = new ArrayList<>();
    while (e.hasMoreElements()) {
      list.add(e.nextElement());
    }
    return (T[]) list.toArray(new Object[list.size()]);
  }

  /**
   * Concatenate the specified string arrays.
   * 
   * @param arrays
   *          The String arrays to concatenate
   * 
   * @return An array containing all strings in the specified arrays
   */
  public static String[] concat(String[]... arrays) {
    int x = 0;
    for (int i = 0; i < arrays.length; ++i)
      x += arrays[i].length;
    String[] result = new String[x];
    x = 0;
    for (int i = 0; i < arrays.length; ++i) {
      System.arraycopy(arrays[i], 0, result, x, arrays[i].length);
      x += arrays[i].length;
    }
    return result;
  }

  /**
   * Concatenate the specified arrays.
   * 
   * @param arrays
   *          The arrays to concatenate
   * 
   * @return An array containing all objects in the specified arrays
   */
  public static Object[] concat(Object[]... arrays) {
    int x = 0;
    for (int i = 0; i < arrays.length; ++i)
      x += arrays[i].length;
    Object[] result = new Object[x];
    x = 0;
    for (int i = 0; i < arrays.length; ++i) {
      System.arraycopy(arrays[i], 0, result, x, arrays[i].length);
      x += arrays[i].length;
    }
    return result;
  }

  /**
   * Whether or not the specified object is in the specified array. This method only compares object references (using the == operator) to
   * determine the outcome.
   * 
   * @param obj
   *          The object
   * @param objs
   *          The array
   * 
   * @return Whether or not the array contains a reference to the object
   * 
   * @see #has(Object, Object...)
   */
  /*
   * Do not change to non-generic form, even though only comparing references, otherwise clients will have to make unnecessary casts (or get
   * a compiler warning).
   */
  @SafeVarargs
  public static <T> boolean in(T obj, T... objs) {
    for (T o : objs) {
      if (obj == o)
        return true;
    }
    return false;
  }

  /**
   * Whether or not the specified character is in the specified array of characters.
   * 
   * @param c
   *          The character The character to search for
   * @param chars
   *          The array of characters to search in
   * 
   * @return Whether or not the specified character is in the specified array of characters
   */
  public static boolean in(char c, char... chars) {
    for (char ch : chars) {
      if (ch == c)
        return true;
    }
    return false;
  }

  /**
   * Whether or not the specified object is in the specified array. This method <i>does</i> use the equals() method to test for the presence
   * of the object.
   * 
   * @param obj
   *          The object
   * @param objs
   *          The array
   * 
   * @return Whether or not the specified object is in the specified array.
   * 
   * @see #in(Object, Object...)
   * 
   */
  @SafeVarargs
  public static <T> boolean has(T obj, T... objs) {
    if (obj == null)
      return in(obj, objs);
    for (T o : objs) {
      if (o == null)
        return false;
      if (o.equals(obj))
        return true;
    }
    return false;
  }

}
