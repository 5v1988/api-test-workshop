package com.qg.tests.basic;

import com.qg.TestConstants;
import com.qg.tests.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.*;

public class DeleteProfileTests extends BaseTests {

  @Parameters({"valid-delete-id"})
  @Test(description = "Delete a profile using the valid id")
  public void deleteProfileUsingIdTest(int id) {
    String responseString = given().log().all().pathParam("id", id)
        .header("Content-Type", "application/json").baseUri(TestConstants.BASE_URL)
        .when()
        .request().delete(DELETE_BY_ID_ENDPOINT)
        .then()
        .log().all()
        .assertThat().statusCode(200)
        .extract().asString();
    Assert.assertEquals(from(responseString).getString("message"), "Profile is deleted",
        "Assert that the delete message is as expected");
    Assert.assertEquals(from(responseString).getInt("id"), id,
        "Assert that the delete id matches!");
  }

  @Parameters({"invalid-delete-id"})
  @Test(description = "Delete a profile using a invalid id")
  public void deleteProfileUsingInValidIdTest(int id) {
    given().log().all()
        .pathParam("id", id).header("Content-Type", "application/json")
        .baseUri(TestConstants.BASE_URL)
        .when()
        .request().delete(DELETE_BY_ID_ENDPOINT)
        .then()
        .assertThat().statusCode(404)
        .assertThat().body("message", equalTo("Not found"));
  }

}
