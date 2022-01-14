package cz.eleveo.qa.tests;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

import cz.eleveo.qa.basic.BaseGuiTestClass;
import cz.eleveo.qa.regiojet.entities.SearchCriterion;
import cz.eleveo.qa.regiojet.pages.HomePage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegioJetGuiTest extends BaseGuiTestClass {

  private static final String URL = "https://novy.regiojet.cz/sk";
  private static final String DEPARTURE_CITY = "Brno";
  private static final String ARRIVAL_CITY = "Ostrava";
  private static final LocalDate DEPARTURE_TIME =
      LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

  @Test
  @DisplayName("Find fastest arrival time connection")
  public void fastestArrivalTimeTest() {
    open(URL);
    var resultsPage =
        new HomePage()
            .searchWithCriterion(
                SearchCriterion.builder()
                    .cityFrom(DEPARTURE_CITY)
                    .cityTo(ARRIVAL_CITY)
                    .departureDate(DEPARTURE_TIME)
                    .build());
    Assertions.assertTrue(
        resultsPage.getSearchModule().getFromCityValue().contains(DEPARTURE_CITY),
        "Departure city should be " + DEPARTURE_CITY);
    Assertions.assertTrue(
        resultsPage.getSearchModule().getToCityValue().contains(ARRIVAL_CITY),
        "Arrival city should be " + ARRIVAL_CITY);
    Assertions.assertTrue(
        resultsPage
            .getSearchModule()
            .getDepartureDateValue()
            .contains(String.valueOf(DEPARTURE_TIME.getDayOfMonth())),
        "Departure time should contain day: " + DEPARTURE_TIME.getDayOfMonth());

    var seatSelectionPage = resultsPage.getFastestArrivalTimeConnection();
    Assertions.assertTrue(
        seatSelectionPage.getDirectionValue().contains(DEPARTURE_CITY)
            && seatSelectionPage.getDirectionValue().contains(ARRIVAL_CITY),
        String.format(
            "Direction should have contain Departure (%s) and Arrival (%s) cities",
            DEPARTURE_CITY, ARRIVAL_CITY));
    Assertions.assertTrue(
        seatSelectionPage
            .getDepartureArrivalDateTime()
            .contains(DEPARTURE_TIME.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
        "Departure date should be :" + DEPARTURE_TIME);
    Assertions.assertEquals(
        seatSelectionPage.getConnectionCard().getJourneyPriceCzk(),
        seatSelectionPage.getPriceValue(),
        "Journey price should be equal to expected one");

    screenshot("Fastest_Arrival_Time_SUCCESS");
  }

  @Test
  @DisplayName("Find cheapest connection")
  public void cheapestArrivalTimeTest() {
    open(URL);
    var resultsPage =
        new HomePage()
            .searchWithCriterion(
                SearchCriterion.builder()
                    .cityFrom(DEPARTURE_CITY)
                    .cityTo(ARRIVAL_CITY)
                    .departureDate(DEPARTURE_TIME)
                    .build());
    Assertions.assertTrue(
        resultsPage.getSearchModule().getFromCityValue().contains(DEPARTURE_CITY),
        "Departure city should be " + DEPARTURE_CITY);
    Assertions.assertTrue(
        resultsPage.getSearchModule().getToCityValue().contains(ARRIVAL_CITY),
        "Arrival city should be " + ARRIVAL_CITY);
    Assertions.assertTrue(
        resultsPage
            .getSearchModule()
            .getDepartureDateValue()
            .contains(String.valueOf(DEPARTURE_TIME.getDayOfMonth())),
        "Departure time should contain day: " + DEPARTURE_TIME.getDayOfMonth());

    var seatSelectionPage = resultsPage.getCheapestConnection();

    Assertions.assertTrue(
        seatSelectionPage.getDirectionValue().contains(DEPARTURE_CITY)
            && seatSelectionPage.getDirectionValue().contains(ARRIVAL_CITY),
        String.format(
            "Direction should have contain Departure (%s) and Arrival (%s) cities",
            DEPARTURE_CITY, ARRIVAL_CITY));
    Assertions.assertTrue(
        seatSelectionPage
            .getDepartureArrivalDateTime()
            .contains(DEPARTURE_TIME.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
        "Departure date should be :" + DEPARTURE_TIME);
    Assertions.assertEquals(
        seatSelectionPage.getConnectionCard().getJourneyPriceCzk(),
        seatSelectionPage.getPriceValue(),
        "Journey price should be equal to expected one");
    screenshot("Cheapest_SUCCESS");
  }

  @Test
  @DisplayName("Find shortest time spent connection")
  public void shortestTimeSpentTest() {
    open(URL);
    var resultsPage =
        new HomePage()
            .searchWithCriterion(
                SearchCriterion.builder()
                    .cityFrom(DEPARTURE_CITY)
                    .cityTo(ARRIVAL_CITY)
                    .departureDate(DEPARTURE_TIME)
                    .build());
    Assertions.assertTrue(
        resultsPage.getSearchModule().getFromCityValue().contains(DEPARTURE_CITY),
        "Departure city should be " + DEPARTURE_CITY);
    Assertions.assertTrue(
        resultsPage.getSearchModule().getToCityValue().contains(ARRIVAL_CITY),
        "Arrival city should be " + ARRIVAL_CITY);
    Assertions.assertTrue(
        resultsPage
            .getSearchModule()
            .getDepartureDateValue()
            .contains(String.valueOf(DEPARTURE_TIME.getDayOfMonth())),
        "Departure time should contain day: " + DEPARTURE_TIME.getDayOfMonth());

    var seatSelectionPage = resultsPage.getShortestTimeSpentConnection();

    Assertions.assertTrue(
        seatSelectionPage.getDirectionValue().contains(DEPARTURE_CITY)
            && seatSelectionPage.getDirectionValue().contains(ARRIVAL_CITY),
        String.format(
            "Direction should have contain Departure (%s) and Arrival (%s) cities",
            DEPARTURE_CITY, ARRIVAL_CITY));
    Assertions.assertTrue(
        seatSelectionPage
            .getDepartureArrivalDateTime()
            .contains(DEPARTURE_TIME.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
        "Departure date should be :" + DEPARTURE_TIME);
    Assertions.assertEquals(
        seatSelectionPage.getConnectionCard().getJourneyPriceCzk(),
        seatSelectionPage.getPriceValue(),
        "Journey price should be equal to expected one");
    screenshot("Shortest_Time_Spent_SUCCESS");
  }
}
