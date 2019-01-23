package nl.naturalis.oaipmh.geneious.plates;

import java.util.ArrayList;
import java.util.List;

import nl.naturalis.oaipmh.api.OAIPMHRequest;
import nl.naturalis.oaipmh.geneious.AnnotatedDocument;
import nl.naturalis.oaipmh.geneious.DocumentNotes;
import nl.naturalis.oaipmh.geneious.DocumentNotes.Note;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPostProcessor;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentPreFilter;
import nl.naturalis.oaipmh.geneious.IAnnotatedDocumentSetFilter;
import nl.naturalis.oaipmh.geneious.ListRecordsHandler;
import nl.naturalis.oaipmh.geneious.jaxb.DnaExtractPlate;
import nl.naturalis.oaipmh.geneious.jaxb.ExtractPlateUnit;
import nl.naturalis.oaipmh.geneious.jaxb.Geneious;
import nl.naturalis.oaipmh.util.ConfigObject;

/**
 * Handles ListRecords requests for extract plates.
 * 
 * @author Ayco Holleman
 *
 */
class DnaExtractPlateListRecordsHandler extends ListRecordsHandler {

  public DnaExtractPlateListRecordsHandler(ConfigObject config, OAIPMHRequest request) {
    super(config, request);
  }

  @Override
  protected List<IAnnotatedDocumentPreFilter> getAnnotatedDocumentPreFilters() {
    List<IAnnotatedDocumentPreFilter> filters = new ArrayList<>(1);
    filters.add(new DnaExtractPlatePreFilter());
    return filters;
  }

  @Override
  protected List<IAnnotatedDocumentPostFilter> getAnnotatedDocumentPostFilters() {
    return new ArrayList<>(0);
  }

  @Override
  protected List<IAnnotatedDocumentSetFilter> getAnnotatedDocumentSetFilters() {
    List<IAnnotatedDocumentSetFilter> filters = new ArrayList<>(1);
    filters.add(new DnaExtractPlateSetFilter());
    return filters;
  }

  @Override
  protected List<IAnnotatedDocumentPostProcessor> getAnnotatedDocumentPostProcessors() {
    return new ArrayList<>(0);
  }

  @Override
  protected void setMetadata(Geneious geneious, AnnotatedDocument ad) {
    geneious.setDnaExtractPlate(createDnaExtractPlate(ad));
  }

  private static DnaExtractPlate createDnaExtractPlate(AnnotatedDocument ad) {
    DnaExtractPlate plate = new DnaExtractPlate();
    ExtractPlateUnit unit = new ExtractPlateUnit();
    DocumentNotes notes = ad.getDocument().getNotes();
    unit.setInstitutePlateID(notes.get(Note.ExtractPlateNumberCode_Samples));
    plate.setUnit(unit);
    return plate;
  }

  @Override
  protected int getPageSize() {
    return config.getInt("dna-extract-plates.repo.pagesize");
  }


}
