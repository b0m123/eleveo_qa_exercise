package cz.eleveo.qa.tests;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.core.IsNot.not;

import cz.eleveo.qa.basic.BaseApiTestClass;
import cz.eleveo.qa.regiojet.pojos.RouteResponsePojo;
import io.qameta.allure.Allure;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegioJetApiTest extends BaseApiTestClass {

  private static final Logger LOGGER = LoggerFactory.getLogger(RegioJetApiTest.class);

  private static final String DEPARTURE_CITY = "Brno";
  private static final String ARRIVAL_CITY = "Ostrava";
  private static final LocalDate DEPARTURE_TIME =
      LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
  private static String regioJetGetRoutesApiEndpoint = "";

  @BeforeAll
  public static void precondition() {

    var response =
        when().get("/restapi/consts/locations").then().statusCode(200).extract().response();

    var departureCityCode =
        response
            .getBody()
            .jsonPath()
            .get(
                "find { it -> it.code == 'CZ' }.cities.find { it -> it.name == '"
                    + DEPARTURE_CITY
                    + "'}.id")
            .toString();
    var arrivalCityCode =
        response
            .body()
            .jsonPath()
            .get(
                "find { it -> it.code == 'CZ' }.cities.find { it -> it.name == '"
                    + ARRIVAL_CITY
                    + "'}.id")
            .toString();

    regioJetGetRoutesApiEndpoint =
        String.format(
            "/restapi/routes/search/simple?tariffs=REGULAR&toLocationType=CITY&toLocationId=%s&fromLocationType=CITY&fromLocationId=%s&departureDate=%s",
            arrivalCityCode,
            departureCityCode,
            DEPARTURE_TIME.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
  }

  @Test
  @DisplayName("Find fastest arrival time connection")
  public void fastestArrivalTimeTest() {
    List<RouteResponsePojo> list =
        new ArrayList<>(
            when()
                .get(regioJetGetRoutesApiEndpoint)
                .then()
                .statusCode(200)
                .body("routes", not(emptyArray()))
                .extract()
                .response()
                .getBody()
                .jsonPath()
                .getList("routes", RouteResponsePojo.class));
    list.sort(Comparator.comparing(RouteResponsePojo::getArrivalTime));
    var route = list.get(0);
    Assertions.assertTrue(route.getDepartureTime() != null, "Departure time is incorrect");
    Assertions.assertEquals(
        DEPARTURE_TIME.getDayOfMonth(),
        route
            .getDepartureTime()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .getDayOfMonth(),
        "Departure days of month should be equal");
    Assertions.assertTrue(route.getPriceFrom() >= 0, "Price is incorrect");
    Assertions.assertTrue(route.getArrivalTime() != null, "Arrival time is incorrect");
    Assertions.assertTrue(
        route.getVehicleTypes() != null && !route.getVehicleTypes().isEmpty(),
        "VehicleType collection should not be empty");
    Assertions.assertTrue(route.getArrivalStationId() > 0, "Arrival station ID is incorrect");
    Assertions.assertTrue(route.getDepartureStationId() > 0, "Departure station ID is incorrect");
    Allure.addAttachment("FASTEST ARRIVAL TIME ROUTE from Brno to Ostrava", route.toString());
    LOGGER.info("FASTEST ARRIVAL TIME ROUTE from Brno to Ostrava: {}", route);
  }

  @Test
  @DisplayName("Find cheapest connection")
  public void cheapestArrivalTimeTest() {
    List<RouteResponsePojo> list =
        new ArrayList<>(
            when()
                .get(regioJetGetRoutesApiEndpoint)
                .then()
                .statusCode(200)
                .body("routes", not(emptyArray()))
                .extract()
                .response()
                .getBody()
                .jsonPath()
                .getList("routes", RouteResponsePojo.class));
    list.sort(Comparator.comparing(RouteResponsePojo::getPriceFrom));
    var route = list.get(0);
    Assertions.assertTrue(route.getDepartureTime() != null, "Departure time is incorrect");
    Assertions.assertEquals(
        DEPARTURE_TIME.getDayOfMonth(),
        route
            .getDepartureTime()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .getDayOfMonth(),
        "Departure days of month should be equal");
    Assertions.assertTrue(route.getPriceFrom() >= 0, "Price is incorrect");
    Assertions.assertTrue(route.getArrivalTime() != null, "Arrival time is incorrect");
    Assertions.assertTrue(
        route.getVehicleTypes() != null && !route.getVehicleTypes().isEmpty(),
        "VehicleType collection should not be empty");
    Assertions.assertTrue(route.getArrivalStationId() > 0, "Arrival station ID is incorrect");
    Assertions.assertTrue(route.getDepartureStationId() > 0, "Departure station ID is incorrect");
    Allure.addAttachment("CHEAPEST ROUTE from Brno to Ostrava", route.toString());
    LOGGER.info("CHEAPEST ROUTE from Brno to Ostrava: {}", route);
  }

  @Test
  @DisplayName("Find shortest time spent connection")
  public void shortestTimeSpentTest() {
    List<RouteResponsePojo> list =
        new ArrayList<>(
            when()
                .get(regioJetGetRoutesApiEndpoint)
                .then()
                .statusCode(200)
                .body("routes", not(emptyArray()))
                .extract()
                .response()
                .getBody()
                .jsonPath()
                .getList("routes", RouteResponsePojo.class));
    list.sort(Comparator.comparing(RouteResponsePojo::getJourneyDuration).reversed());
    var route = list.get(0);
    Assertions.assertTrue(route.getDepartureTime() != null, "Departure time is incorrect");
    Assertions.assertEquals(
        DEPARTURE_TIME.getDayOfMonth(),
        route
            .getDepartureTime()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .getDayOfMonth(),
        "Departure days of month should be equal");
    Assertions.assertTrue(route.getPriceFrom() >= 0, "Price is incorrect");
    Assertions.assertTrue(route.getArrivalTime() != null, "Arrival time is incorrect");
    Assertions.assertTrue(
        route.getVehicleTypes() != null && !route.getVehicleTypes().isEmpty(),
        "VehicleType collection should not be empty");
    Assertions.assertTrue(route.getArrivalStationId() > 0, "Arrival station ID is incorrect");
    Assertions.assertTrue(route.getDepartureStationId() > 0, "Departure station ID is incorrect");
    Allure.addAttachment("SHORTEST TIME SPENT ROUTE from Brno to Ostrava", route.toString());
    LOGGER.info("SHORTEST TIME SPENT ROUTE from Brno to Ostrava: {}", route);
  }
}
