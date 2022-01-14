package cz.eleveo.qa.regiojet.pages;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import cz.eleveo.qa.regiojet.entities.ConnectionCard;
import io.qameta.allure.Step;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SeatSelectionPage {

  @Getter private ConnectionCard connectionCard;

  // elements
  private SelenideElement directionTitle = $x("//h2[contains(@class, 'center')]");
  private SelenideElement priceHolder = $x("(//div//h3[contains(@class, 'h3') ])[last()]");
  private SelenideElement departureArrivalDateTime =
      $x("//ul/li[2 and contains(@class, 'font-bold')]");

  public SeatSelectionPage(ConnectionCard connectionCard) {
    this.connectionCard = connectionCard;
    new SelectPlanPage()
        .selectPlanByPrice(this.connectionCard.getJourneyPriceCzk())
        .declinePlanUpdating();
  }

  // PO steps
  @Step("Get Direction")
  public String getDirectionValue() {
    return directionTitle.should(Condition.visible, Duration.ofSeconds(30)).getText().trim();
  }

  @Step("Get Price")
  public BigDecimal getPriceValue() {
    return new BigDecimal(
        priceHolder
            .should(Condition.visible, Duration.ofSeconds(30))
            .getText()
            .trim()
            .replaceAll("[^\\d\\/]", ""));
  }

  @Step("Get Departure-Arrival date time")
  public String getDepartureArrivalDateTime() {
    return departureArrivalDateTime
        .should(Condition.visible, Duration.ofSeconds(30))
        .getText()
        .trim();
  }
}
