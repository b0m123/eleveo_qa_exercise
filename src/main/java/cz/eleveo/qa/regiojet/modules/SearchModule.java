package cz.eleveo.qa.regiojet.modules;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import java.time.LocalDate;

public class SearchModule {

  // Search module locators
  private SelenideElement searchContainer = $x("//div[@role = 'search']");
  private SelenideElement searchFromInput = $x("(//input[not(@type = 'hidden')])[1]");
  private SelenideElement searchFromPlaceholder = $x("(//div[contains(@class, 'singleValue')])[1]");
  private SelenideElement searchToInput = $x("(//input[not(@type = 'hidden')])[2]");
  private SelenideElement searchToPlaceholder = $x("(//div[contains(@class, 'singleValue')])[2]");
  private SelenideElement searchDepartureDatePicker =
      searchContainer.$x(".//div[@data-id = 'departure-date']");
  private SelenideElement searchArrivalDatePicker =
      searchContainer.$x(".//div[@data-id = 'arrival-date']");
  private SelenideElement searchButton = $x("//*[@data-id = 'search-btn']");
  // DayPicker module
  private SelenideElement dayPickerContainer =
      $x("//*[contains(@class, 'DayPicker') and contains(@class, 'Container')]");

  // PO steps
  @Step("Fill field 'City FROM': {0}")
  public SearchModule fillCityFrom(String cityFrom) {
    searchFromInput.should(Condition.enabled).setValue(cityFrom).pressEnter();
    return page(this);
  }

  @Step("Fill field 'City To': {0}")
  public SearchModule fillCityTo(String cityTo) {
    searchToInput.should(Condition.enabled).setValue(cityTo).pressEnter();
    return page(this);
  }

  @Step("Pick departure date: {0}")
  public SearchModule pickDepartureDate(LocalDate date) {
    searchDatePicker(searchDepartureDatePicker, date);
    return page(this);
  }

  @Step("Pick arrival date: {0}")
  public SearchModule pickArrivalDate(LocalDate date) {
    searchDatePicker(searchArrivalDatePicker, date);
    return page(this);
  }

  @Step("Click 'Search' button")
  public SearchModule clickSearchButton() {
    searchButton.should(enabled).click();
    return page(this);
  }

  @Step("Get 'From City' input value")
  public String getFromCityValue() {
    return searchFromPlaceholder.should(exist, Duration.ofSeconds(5)).getText();
  }

  @Step("Get 'To City' input value")
  public String getToCityValue() {
    return searchToPlaceholder.should(exist, Duration.ofSeconds(5)).getText();
  }

  @Step("Get 'Departure date' input value")
  public String getDepartureDateValue() {
    return searchDepartureDatePicker.should(exist, Duration.ofSeconds(5)).getText();
  }

  @Step("Get 'Arrival date' input value")
  public String getArrivalDateValue() {
    return searchArrivalDatePicker.should(exist, Duration.ofSeconds(5)).getText();
  }

  // private support methods
  @Step("Pick date...")
  private SelenideElement searchDatePicker(SelenideElement datePickerElement, LocalDate date) {
    // There is no way to fill date directly to input field (on page).
    // So here we can implement fancy date picker that supports dates in big range.
    // But I will leave it simple
    datePickerElement.should(enabled).click();
    dayPickerContainer.should(visible, Duration.ofSeconds(5));
    dayPickerContainer
        .$x(
            String.format(
                ".//td[not(contains(@class, 'blocked')) and contains(@class, 'Day') and text() = '%s']",
                date.getDayOfMonth()))
        .should(visible)
        .click();
    dayPickerContainer.should(disappear, Duration.ofSeconds(5));
    return datePickerElement;
  }
}
