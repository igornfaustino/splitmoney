package com.nfaustino.splitmoney.groups.infra.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupControllerTest {
    @LocalServerPort
    private int port;

    @Test
    void should_createNewGroup() throws JSONException {
        var response = given()
                .port(port)
                .body(new JSONObject().put("name", "Group 1").toString())
                .header("Content-Type", "application/json")
                .when()
                .post("/v1/group");

        response.then()
                .assertThat().statusCode(200)
                .body("data.id", notNullValue())
                .body("data.name", equalTo("Group 1"))
                .body("success", equalTo(true))
                .body("message", emptyOrNullString());
    }

    @Test
    void Should_ReturnErrorWhenBodyIsInvalid() throws JSONException {
        var response = given()
                .port(port)
                .body(new JSONObject().put("wrong", "Group 1").toString())
                .header("Content-Type", "application/json")
                .when()
                .post("/v1/group");

        response.then()
                .assertThat().statusCode(422)
                .body("success", equalTo(false))
                .body("message", equalTo("Name is required"));
    }
}
