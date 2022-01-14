package cz.eleveo.qa.regiojet.listeners;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegioJetPageListener implements WebDriverListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegioJetPageListener.class);

  private SelenideElement acceptCookiesBannerButton =
      $x(
          "//div[contains(@class, 'page-layout') and ./*[contains(text(), 'cookie')]]//button[last()]");

  public void afterTo(WebDriver.Navigation navigation, String url) {
    acceptCookies();
  }

  public void beforeClick(WebElement element) {
    acceptCookies();
  }

  @Step("Accept Cookies")
  private void acceptCookies() {
    try {
      if (acceptCookiesBannerButton.has(Condition.visible))
        acceptCookiesBannerButton.should(Condition.enabled).click(ClickOptions.usingJavaScript());
    } catch (ElementShould | ElementNotFound e) {
      LOGGER.debug("Cookies acceptance button not found, skip it");
    }
  }
}
