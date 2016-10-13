package nl.naturalis.oaipmh.geneious;

import java.util.EnumMap;

/**
 * A {@code DocumentFields} instance maintains the values of all possibly
 * relevant elements under the &lt;fields&gt; element within the document_xml
 * column.
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentFields {

	/**
	 * Enumerates all XML element tag names underneath the &lt;fields&gt;
	 * element of the document_xml column that might be used for OAI-PMH
	 * generation.
	 */
	public static enum Field {
		sequence_length
	}

	private EnumMap<Field, String> data = new EnumMap<>(Field.class);

	/**
	 * Returns the number of notes extracted from the XML in the document_xml
	 * column.
	 * 
	 * @return
	 */
	public int count()
	{
		return data.size();
	}

	/**
	 * Whether or not the document_xml column contains the specified
	 * &lt;note&gt; element.
	 * 
	 * @param note
	 * @return
	 */
	public boolean isSet(Field note)
	{
		return data.containsKey(note);
	}

	/**
	 * Set the value of the specified note.
	 * 
	 * @param field
	 * @param value
	 */
	public void set(Field field, String value)
	{
		data.put(field, value);
	}

	/**
	 * Get the value of the specified note.
	 * 
	 * @param note
	 * @return
	 */
	public String get(Field note)
	{
		return data.get(note);
	}

}
