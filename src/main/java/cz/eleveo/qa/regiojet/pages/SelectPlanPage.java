package cz.eleveo.qa.regiojet.pages;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.math.BigDecimal;

public class SelectPlanPage {

  private SelenideElement declineUpdatePlanSuggestionButton =
      $x("//div[contains(@class, 'modal-wrapper')]//button[1]");

  @Step("Select Plan page: select plan by price {0}")
  public SelectPlanPage selectPlanByPrice(BigDecimal price) {
    var selectPlanButton = $x(String.format("//button[contains(., '%s')]", price.toString()));
    if (selectPlanButton.exists() && selectPlanButton.isDisplayed()) {
      selectPlanButton.click();
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
