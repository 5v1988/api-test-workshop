package com.qg.tests.basic;

import com.qg.TestConstants;
import com.qg.tests.BaseTests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateProfileTests extends BaseTests {

  @Parameters({"create-profile-payload"})
  @Test(description = "Create a profile using the given payload")
  public void createNewProfileUsingFileTest(String fileName) {
    File payload = new File(String.format("%s/%s", PAYLOAD_DIR, fileName));
    ValidatableResponse response = given().log().all()
        .header("Content-Type", "application/json")
        .baseUri(TestConstants.BASE_URL)
        .when().request().body(payload)
        .post(CREATE_ENDPOINT)
        .then().log().all();
    response.assertThat().statusCode(201)
        .assertThat().body("message", equalTo("Profile is added"))
        .assertThat().body("id", greaterThan(0));
  }

  @Test(description = "Create a profile using map")
  public void createNewProfileUsingMapTest() {
    //Build request payload
    Map<String, Object> payload = new HashMap<>();
    payload.put("name", "Michael");
    payload.put("gender", "male");
    payload.put("age", 35);
    payload.put("location", "Ireland");
    List<String> socialProfiles = new ArrayList<>();
    socialProfiles.add("Twitter");
    payload.put("is_employed", false);
    List<String> hobbies = new ArrayList<>();
    hobbies.add("Blogging");
    payload.put("hobbies", hobbies);
    Response response = given().log().all()
        .contentType(ContentType.JSON)
        .baseUri(TestConstants.BASE_URL)
        .when().request().body(payload)
        .post(CREATE_ENDPOINT)
        .then().extract().response();
    //assert after extracting values from the response
    Assert.assertEquals(response.path("message"), "Profile is added");
    int id = response.path("id");
    Assert.assertTrue(id > 0, String.format("Id : %d is verified!", id));
  }

}
