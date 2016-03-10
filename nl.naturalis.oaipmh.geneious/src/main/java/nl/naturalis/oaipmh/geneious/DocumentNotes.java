package nl.naturalis.oaipmh.geneious;

import java.util.EnumMap;

/**
 * A {@code DocumentNotes} instance maintains the values of all relevant notes
 * within the document_xml column. The document_xml column may contain a
 * &lt;notes&gt; element. The &lt;notes&gt; element contains one or more
 * &lt;note&gt; elements. Each &lt;note&gt; element contains one child element
 * whose name identifies the note, for example &lt;BOLDIDCode_Bold&gt;. Not all
 * of these child elements are relevant to the OAI-PMH generation process. Only
 * those whose name corresponds to a {@link Note} constant are extracted from
 * the XML.
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentNotes {

	/**
	 * Enumerates all XML elements that contain a note that might be used for
	 * OAI-PMH generation. These elements are nested within a &lt;note&gt;
	 * element.
	 * 
	 * @author Ayco Holleman
	 *
	 */
	public static enum Note {
		/**
		 * Flag set to "true" to indicate that CRS data has been imported.
		 */
		CRSCode_CRS,
		/**
		 * Maps to associatedUnitID for specimens.
		 */
		BOLDIDCode_Bold,
		/**
		 * Maps to uri for specimens.
		 */
		BOLDURICode_FixedValue_Bold,
		/**
		 * Maps to consensusSequenceLength for DNA extracts.
		 */
		NucleotideLengthCode_Bold,
		/**
		 * Maps to geneticAccessionNumber for DNA extracts.
		 */
		GenBankIDCode_Bold,
		/**
		 * Maps to geneticAccessionNumberURI for DNA extracts.
		 */
		GenBankURICode_FixedValue_Bold,
		/**
		 * Maps to multiMediaObjectComment for specimens.
		 */
		NumberOfImagesCode_Bold,
		/**
		 * Maps to unitID for specimens; maps to associatedUnitID for DNA
		 * extracts.
		 */
		RegistrationNumberCode_Samples,
		/**
		 * Maps to institutePlateID
		 */
		ExtractPlateNumberCode_Samples,
		/**
		 * Maps to unitID for DNA extracts.
		 */
		ExtractIDCode_Samples,
		/**
		 * Maps to platePosition for DNA extracts.
		 */
		PlatePositionCode_Samples,
		/**
		 * Maps to batchID for DNA extracts.
		 */
		ProjectPlateNumberCode_Samples,
		/**
		 * Maps to sequencingStaff for DNA extracts.
		 */
		SequencingStaffCode_FixedValue_Samples,
		/**
		 * Maps to versionNumber for DNA extracts.
		 */
		DocumentVersionCode_Seq,
		/**
		 * Maps to marker for DNA extracts.
		 */
		MarkerCode_Seq,
		/**
		 * Maps to pcrPlateID for DNA extracts.
		 */
		PCRplateIDCode_Seq,
		/**
		 * Maps to consensusSequenceQuality for DNA extracts.
		 */
		ConsensusSeqPassCode_Seq,
		/**
		 * Maps to amplificationStaff for DNA extracts.
		 */
		AmplicificationStaffCode_FixedValue_Seq
	}

	private EnumMap<Note, String> data = new EnumMap<>(Note.class);

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
	public boolean isSet(Note note)
	{
		return data.containsKey(note);
	}

	/**
	 * Set the value of the specified note.
	 * 
	 * @param note
	 * @param value
	 */
	public void set(Note note, String value)
	{
		data.put(note, value);
	}

	/**
	 * Get the value of the specified note.
	 * 
	 * @param note
	 * @return
	 */
	public String get(Note note)
	{
		return data.get(note);
	}

}
