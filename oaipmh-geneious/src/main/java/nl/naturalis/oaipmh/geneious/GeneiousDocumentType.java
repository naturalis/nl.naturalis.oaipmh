package nl.naturalis.oaipmh.geneious;

/**
 * An enumeration of a possible values for the "class" attribute of the
 * &lt;document&gt; element in the document_xml column.
 * 
 * @author Ayco Holleman
 *
 */
public enum GeneiousDocumentType {

	GenBankNucleotideSequence("com.biomatters.plugins.ncbi.documents.GenBankNucleotideSequence"),
	OligoSequenceDocument("com.biomatters.geneious.publicapi.implementations.sequence.OligoSequenceDocument"),
	NucleotideGeneDocument("com.biomatters.plugins.ncbi.NucleotideGeneDocument"),
	DefaultNucleotideSequence("com.biomatters.geneious.publicapi.implementations.sequence.DefaultNucleotideSequence"),
	PopSetDocument("com.biomatters.plugins.ncbi.popset.PopSetDocument"),
	OperationRecordDocument("com.biomatters.geneious.publicapi.documents.OperationRecordDocument"),
	DefaultAlignmentDocument("com.biomatters.geneious.publicapi.implementations.DefaultAlignmentDocument"),
	DefaultPhylogenyDocument("com.biomatters.geneious.publicapi.implementations.DefaultPhylogenyDocument"),
	DefaultNucleotideGraphSequence("com.biomatters.geneious.publicapi.implementations.sequence.DefaultNucleotideGraphSequence"),
	ABIPluginDocument("com.biomatters.plugins.abi.ABIPluginDocument");

	public static GeneiousDocumentType parse(String className)
	{
		for (GeneiousDocumentType type : values()) {
			if (type.className.equals(className))
				return type;
		}
		return null;
	}

	private final String className;

	private GeneiousDocumentType(String className)
	{
		this.className = className;
	}
}