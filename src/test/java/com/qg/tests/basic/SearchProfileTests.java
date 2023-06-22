package com.qg.tests.basic;

import com.qg.TestConstants;
import com.qg.tests.BaseTests;
import io.restassured.response.Response;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SearchProfileTests extends BaseTests {

  private static Logger logger = LoggerFactory.getLogger(SearchProfileTests.class);

  @BeforeClass
  public void setUpBeforeClass() {
    logger.info("Starting tests related to search profiles");
  }

  @Test(description = "Get all profiles")
  public void getAllProfilesTest() {
    given().log().all().baseUri(TestConstants.BASE_URL)
        .when().get(TestConstants.SEARCH_ALL_ENDPOINT)
        .then().log().all()
        .body("name", hasItems("Akhil", "Benjamin"))
        .body("gender", hasItems("male", "female"))
        .body("age", hasItems(25, 30))
        .body("hobbies", hasItems(hasItems("books", "movies"), hasItems("travels", "sports")));
    logger.info(String.format("Completing the test : '%s'", "Get all profiles"));
  }

  @Parameters({"name", "gender", "age"})
  @Test(description = "Get all matching profiles by applying filters")
  public void getProfileUsingFilterTest(String name, String gender, String age) {
    name = Objects.isNull(name) ? "" : name;
    gender = Objects.isNull(gender) ? "" : gender;
    age = Objects.isNull(age) ? "" : age;
    given().log().uri().baseUri(TestConstants.BASE_URL)
        .with().queryParam("name", name)
        .queryParam("gender", gender)
        .queryParam("age", age)
        .when().get(TestConstants.SEARCH_ALL_ENDPOINT)
        .then().log().body()
        .body("[0].name", equalTo(name))
        .body("[0].gender", equalTo(gender))
        .body("[0].age", is(Integer.parseInt(age)));
    logger.info(String.format("Completing the test : '%s'", "Get all profiles"));
  }

  @Parameters({"valid-id"})
  @Test(description = "Get a profile with a valid profile id")
  public void getProfileByValidIdTest(int id) {
    given().baseUri(TestConstants.BASE_URL)
        .when().get(TestConstants.SEARCH_BY_ID_ENDPOINT, id)
        .then().log().body()
        .body("id", equalTo(id))
        .body("name", equalTo("Akhil"))
        .body("hobbies", hasItems("books", "movies"))
        .body("attributes",
            allOf(hasEntry("weight", 75), hasEntry("height", 175)))
        .body("attributes",
            allOf(hasEntry("complex", "brown"))
        );
    logger.info(
        String.format("Completing the test : '%s'", "Get a profile with a valid profile id"));
  }

  @Parameters({"invalid-id"})
  @Test(description = "Get a profile with an invalid profile id")
  public void getProfileByInvalidIdTest(int id) {
    Response response = given().log().ifValidationFails().baseUri(TestConstants.BASE_URL)
        .when().get(TestConstants.SEARCH_BY_ID_ENDPOINT, id);
    Assert.assertEquals(response.statusCode(), 404, "Assert that the status code is 200");
    logger.info(
        String.format("Completing the test : '%s'", "Get a profile with an invalid profile id"));
  }

  @AfterClass
  public void afterTest() {
    System.out.println("After test");
  }

}
