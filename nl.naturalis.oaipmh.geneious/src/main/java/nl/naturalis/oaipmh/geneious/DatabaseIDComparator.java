package nl.naturalis.oaipmh.geneious;

import java.util.Comparator;

/**
 * A {@link Comparator} for {@link AnnotatedDocument} instances that selects the
 * instance with the highest database ID.
 * 
 * @author Ayco Holleman
 *
 */
public class DatabaseIDComparator implements Comparator<AnnotatedDocument> {

	public DatabaseIDComparator()
	{
	}

	@Override
	public int compare(AnnotatedDocument ad0, AnnotatedDocument ad1)
	{
		return ad1.getId() - ad0.getId();
	}

}
