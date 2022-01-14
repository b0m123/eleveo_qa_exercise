package cz.eleveo.qa.basic;

import static com.codeborne.selenide.WebDriverRunner.addListener;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import cz.eleveo.qa.regiojet.listeners.RegioJetPageListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseGuiTestClass {

  @BeforeAll
  public static void addListeners() {
    // Custom listener will handle cookies banner after page opened and before every click event
    addListener(new RegioJetPageListener());
  }

  @BeforeEach
  public void setUp() {
    // setup basic timeouts
    Configuration.timeout = 5000; // ms
    Configuration.pageLoadTimeout = 30000; // ms
    // browser size
    Configuration.browserSize = "1600x800";
  }

  @AfterEach
  public void tearDown() {
    // make sure every test has own browser instance
    Selenide.closeWebDriver();
  }
}
