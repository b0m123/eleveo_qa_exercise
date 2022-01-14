package cz.eleveo.qa.regiojet.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import java.math.BigDecimal;
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectPlanPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(SelectPlanPage.class);

  private SelenideElement declineUpdatePlanSuggestionButton =
      $x("//div[contains(@class, 'modal-wrapper')]//button[1]");

  @Step("Select Plan page: select plan by price {0}")
  public SelectPlanPage selectPlanByPrice(BigDecimal price) {
    var selectPlanButton = $x(String.format("//button[contains(., '%s')]", price.toString()));
    try {
      selectPlanButton.should(Condition.visible, Duration.ofSeconds(2)).click();
    } catch (ElementShould | ElementNotFound | NoSuchElementException e) {
      LOGGER.info("Select Plan page not found, proceed...");
    }
    return page(this);
  }

  @Step("Select Plan page: decline update plan suggestion")
  public SeatSelectionPage declinePlanUpdating() {
    if (declineUpdatePlanSuggestionButton.is(Condition.visible))
      declineUpdatePlanSuggestionButton.should(Condition.enabled).click();
    return page(new SeatSelectionPage());
  }
}
