package com.nfaustino.splitmoney.queries.group.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.github.database.rider.core.api.dataset.DataSet;
import com.nfaustino.splitmoney.groups.utils.DatabaseTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupQueryControllerTest extends DatabaseTest {
    @LocalServerPort
    private int port;

    @Test
    @DataSet(value = "valid-group-with-participant.yml", cleanBefore = true, cleanAfter = true)
    void should_GetGroup() {
        var response = given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/v1/group/1000");

        response.then()
                .assertThat().statusCode(200)
                .body("success", equalTo(true))
                .body("data.id", equalTo(1000))
                .body("data.name", equalTo("Test Group"))
                .body("data.participants", hasSize(1))
                .body("data.participants.name", hasItem("Carlos"));
    }

    @Test
    void should_Return404_WhenGroupNotFound() {
        var response = given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/v1/group/1000");

        response.then()
                .assertThat().statusCode(404)
                .body("success", equalTo(false))
                .body("message", equalTo("Group not found with id 1000"));
    }
}
