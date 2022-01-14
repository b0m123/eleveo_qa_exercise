package cz.eleveo.qa.basic;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTestClass {

  @BeforeAll
  public static void setUp() {
    RestAssured.requestSpecification =
        new RequestSpecBuilder()
            .setBaseUri("https://brn-ybus-pubapi.sa.cz")
            .addHeader("X-Currency", "CZK")
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();
  }
}
