package nl.naturalis.oaipmh.geneious;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Set filter shared by all geneious repositories. Filters {@link AnnotatedDocument} records by means of a
 * {@link DocumentVersionComparator}.
 * 
 * @author Ayco Holleman
 *
 */
public class DocumentVersionSetFilter implements IAnnotatedDocumentSetFilter {

  private static final Logger logger = LoggerFactory.getLogger(DocumentVersionSetFilter.class);

  public DocumentVersionSetFilter() {}

  @Override
  public List<AnnotatedDocument> filter(List<AnnotatedDocument> input) {
    if (logger.isDebugEnabled()) {
      logger.debug("Applying filter to {} AnnotatedDocument instances", input.size());
      logger.debug("Marking records for removal using {}",
          DocumentVersionComparator.class.getSimpleName());
    }
    Collections.sort(input, new DocumentVersionComparator());
    List<AnnotatedDocument> result = new ArrayList<>(input.size());
    for (AnnotatedDocument ad : input) {
      if (!ad.doNotOutput) {
        result.add(ad);
      }
    }
    if (logger.isDebugEnabled()) {
      int i = input.size() - result.size();
      logger.debug("Number of removed instances: {}", i);
      logger.debug("Number of instances remaining: {}", result.size());
    }
    return result;
  }

}
