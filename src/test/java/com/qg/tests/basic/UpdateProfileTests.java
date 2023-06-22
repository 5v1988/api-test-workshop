package com.qg.tests.basic;

import com.qg.TestConstants;
import com.qg.tests.BaseTests;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;

public class UpdateProfileTests extends BaseTests {

  @Parameters({"valid-update-id", "location"})
  @Test(description = "Update a profile using the valid id")
  public void updateProfileUsingIdTest(int id, String location) {
    Map<String,String> payload = new HashMap<>();
    payload.put("location", location);
    String responseString = given().log().all().pathParam("id", id)
        .header("Content-Type", "application/json").baseUri(TestConstants.BASE_URL)
        .when()
        .body(payload)
        .request().put(UPDATE_BY_ID_ENDPOINT)
        .then()
        .log().all()
        .assertThat().statusCode(200)
        .extract().asString();
    Assert.assertEquals(from(responseString).getInt("id"), id,
        "Assert that the updated id matches!");
    Assert.assertEquals(from(responseString).getString("location"), location,
        "Assert that the location matches after update");

  }


  @Parameters({"invalid-update-id"})
  @Test(description = "Update a profile using a invalid id")
  public void updateProfileUsingInValidIdTest(int id) {
    given().log().all()
        .pathParam("id", id).header("Content-Type", "application/json")
        .baseUri(TestConstants.BASE_URL)
        .when()
        .request().put(UPDATE_BY_ID_ENDPOINT)
        .then()
        .assertThat().statusCode(404)
        .assertThat().body("message", equalTo("Not found"));
  }
}
