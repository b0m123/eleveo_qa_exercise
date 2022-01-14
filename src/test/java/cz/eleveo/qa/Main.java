package cz.eleveo.qa;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.PrintWriter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class Main {

  private SummaryGeneratingListener listener = new SummaryGeneratingListener();

  public static void main(String[] args) {
    var main = new Main();
    main.runAllTest();
    main.listener.getSummary().printTo(new PrintWriter(System.out));
  }

  public void runAllTest() {
    var request =
        LauncherDiscoveryRequestBuilder.request()
            .selectors(selectPackage("cz.eleveo.qa.tests"))
            .filters(includeClassNamePatterns(".*Test"))
            .build();
    var launcher = LauncherFactory.create();
    launcher.discover(request);
    launcher.registerTestExecutionListeners(listener);
    launcher.execute(request);
  }
}
