package org.sonar.db;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.OrchestratorBuilder;
import com.sonar.orchestrator.locator.FileLocation;
import org.apache.commons.lang.StringUtils;

import java.io.File;

public class CreateDB {

  public static void main(String[] args) {
    System.out.println("app is running!");

    OrchestratorBuilder builder = Orchestrator.builderEnv();
    String version = System.getProperty("sonar.runtimeVersion");
    if (StringUtils.isEmpty(version)) {
      File zip = FileLocation.byWildcardMavenFilename(new File("../../sonar-application/build/distributions"), "sonar-application-*.zip").getFile();
      builder.setZipFile(zip);
    } else {
      builder.setSonarVersion(version);
    }
    builder.setOrchestratorProperty("orchestrator.workspaceDir", "build/it");

    Orchestrator orchestrator = builder.build();
    try {
      orchestrator.start();
    } finally {
      orchestrator.stop();
    }
  }
}
