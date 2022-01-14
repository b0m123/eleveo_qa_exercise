package cz.eleveo.qa.regiojet.pages;

import static com.codeborne.selenide.Selenide.page;

import cz.eleveo.qa.regiojet.entities.SearchCriterion;
import cz.eleveo.qa.regiojet.modules.SearchModule;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;

public class HomePage {

  // modules
  private SearchModule searchModule = new SearchModule();

  // business flow steps
  @Step("Search journey with criterion: {0}")
  public ResultsPage searchWithCriterion(SearchCriterion criterion) {
    if(criterion != null) {
      if (!StringUtils.isEmpty(criterion.getCityFrom()))
        searchModule.fillCityFrom(criterion.getCityFrom());
      if (!StringUtils.isEmpty(criterion.getCityTo()))
        searchModule.fillCityTo(criterion.getCityTo());
      if(null != criterion.getDepartureDate())
        searchModule.pickDepartureDate(criterion.getDepartureDate());
      if(null != criterion.getArrivalDate())
        searchModule.pickArrivalDate(criterion.getArrivalDate());
    }
    searchModule.clickSearchButton();
    return page(new ResultsPage());
  }
}
