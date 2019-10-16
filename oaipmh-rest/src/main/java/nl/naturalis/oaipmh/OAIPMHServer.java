package nl.naturalis.oaipmh;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import nl.naturalis.oaipmh.rest.OAIPMHResource;

public class OAIPMHServer extends Application<OAIPMHConfig> {
  
  public static void main(String[] args) throws Exception {
    new OAIPMHServer().run(args);
  }
  
  @Override
  public String getName() {
    return "OAI-PMH server";
  }

  @Override
  public void run(OAIPMHConfig cfg, Environment env) throws Exception {
    env.jersey().register(new OAIPMHResource());
  }

}
