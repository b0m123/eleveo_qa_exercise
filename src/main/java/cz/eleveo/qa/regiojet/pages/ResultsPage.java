package cz.eleveo.qa.regiojet.pages;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import cz.eleveo.qa.regiojet.entities.ConnectionCard;
import cz.eleveo.qa.regiojet.modules.SearchModule;
import io.qameta.allure.Step;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultsPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResultsPage.class);

  // modules
  @Getter private SearchModule searchModule = new SearchModule();

  // elements
  private ElementsCollection connectionCards = $$x("//div[contains(@data-id, 'connection-card')]");

  // business flow methods
  @Step("Select 'shortest time spent' connection")
  public SeatSelectionPage getShortestTimeSpentConnection() {
    List<ConnectionCard> allConnections = getAllConnectionResults();
    allConnections.sort(Comparator.comparing(ConnectionCard::getDuration));
    LOGGER.info("SHORTEST TIME SPENT connection is: {} ", allConnections.get(0));
    allConnections.get(0).getPriceButton().scrollTo().should(Condition.enabled).click();
    allConnections.get(0).getPriceButton().scrollTo().should(Condition.disappear);
    return page(new SeatSelectionPage(allConnections.get(0)));
  }

  @Step("Select 'cheapest' connection")
  public SeatSelectionPage getCheapestConnection() {
    List<ConnectionCard> allConnections = getAllConnectionResults();
    allConnections.sort(Comparator.comparing(ConnectionCard::getJourneyPriceCzk));
    LOGGER.info("CHEAPEST connection is: {} ", allConnections.get(0));
    allConnections.get(0).getPriceButton().scrollTo().should(Condition.enabled).click();
    allConnections.get(0).getPriceButton().scrollTo().should(Condition.disappear);
    return page(new SeatSelectionPage(allConnections.get(0)));
  }

  @Step("Select 'cheapest' connection")
  public SeatSelectionPage getFastestArrivalTimeConnection() {
    List<ConnectionCard> allConnections = getAllConnectionResults();
    allConnections.sort(Comparator.comparing(ConnectionCard::getArrivalTime));
    LOGGER.info("FASTEST ARRIVAL TIME connection is: {} ", allConnections.get(0));
    allConnections.get(0).getPriceButton().scrollTo().should(Condition.enabled).click();
    allConnections.get(0).getPriceButton().scrollTo().should(Condition.disappear);
    return page(new SeatSelectionPage(allConnections.get(0)));
  }

  // Support methods
  @Step("Get all connection results")
  private List<ConnectionCard> getAllConnectionResults() {

    List<ConnectionCard> result = new ArrayList<>();
    for (SelenideElement element :
        connectionCards.shouldHave(CollectionCondition.sizeNotEqual(0), Duration.ofMinutes(1))) {
      try {
        var fromToHoursString = element.$("h2").getText();
        var durationString = element.$x("./div[1]//span[contains(@class, 'text-13')]").getText();
        var numberOfStopsString =
            element
                .$x("./div[2]//span[contains(@class, 'text-13')]")
                .getText()
                .replaceAll("[^\\d\\/]", "")
                .trim();
        var priceString = element.$x(".//button").getText().replaceAll("[^\\d\\/]", "").trim();
        result.add(
            ConnectionCard.builder()
                .departureTime(
                    LocalDate.MIN.atTime(
                        Integer.parseInt(fromToHoursString.split("-")[0].trim().split(":")[0]),
                        Integer.parseInt(fromToHoursString.split("-")[0].trim().split(":")[1])))
                .arrivalTime(
                    LocalDate.MIN.atTime(
                        Integer.parseInt(fromToHoursString.split("-")[1].trim().split(":")[0]),
                        Integer.parseInt(fromToHoursString.split("-")[1].trim().split(":")[1])))
                .duration(
                    Duration.between(
                        LocalTime.MIN,
                        LocalTime.parse(durationString.replaceAll("[^\\d\\/:]", ""))))
                .numberOfStops(
                    StringUtils.isEmpty(numberOfStopsString)
                        ? 0
                        : Integer.parseInt(numberOfStopsString))
                .journeyPriceCzk(new BigDecimal(priceString))
                .priceButton(element.$x(".//button"))
                .build());
      } catch (ElementNotFound | ElementShould e) {
        LOGGER.error("Can't find element.", e);
      } catch (DateTimeException
          | NumberFormatException
          | ArithmeticException
          | IndexOutOfBoundsException e) {
        LOGGER.error("Error occurred during data parsing.", e);
      }
    }

    return result;
  }
}
