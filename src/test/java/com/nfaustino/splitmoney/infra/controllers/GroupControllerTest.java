package com.nfaustino.splitmoney.infra.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.github.database.rider.core.api.dataset.DataSet;
import com.nfaustino.splitmoney.utils.DatabaseTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupControllerTest extends DatabaseTest {
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
        void Should_ReturnErrorWhenCreatingNewGroup_WhenBodyIsInvalid() throws JSONException {
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

        @Test
        @DataSet(value = "valid-group-with-participant.yml", cleanBefore = true, cleanAfter = true)
        void should_AddParticipantToGroup() throws JSONException {
                var response = given()
                                .port(port)
                                .body(new JSONObject().put("name", "John").toString())
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/v1/group/1000/participant");

                response.then()
                                .assertThat().statusCode(200)
                                .body("success", equalTo(true))
                                .body("message", emptyOrNullString());
        }

        @Test
        void should_ReturnErrorAddParticipantToGroup_IfBodyIsInvalid() throws JSONException {
                var response = given()
                                .port(port)
                                .body(new JSONObject().put("wrong", "Carlos").toString())
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/v1/group/1001001/participant");

                response.then()
                                .assertThat().statusCode(422)
                                .body("success", equalTo(false))
                                .body("message", equalTo("Name is required"));
        }

        @Test
        void should_ReturnErrorAddParticipantToGroup_IfGroupDoesntExists() throws JSONException {
                var response = given()
                                .port(port)
                                .body(new JSONObject().put("name", "Carlos").toString())
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/v1/group/1001/participant");

                response.then()
                                .assertThat().statusCode(400)
                                .body("success", equalTo(false))
                                .body("message", equalTo("Group with id 1001 was not found"));
        }

        @Test
        @DataSet(value = "valid-group-with-participant.yml", cleanBefore = true, cleanAfter = true)
        void should_ReturnErrorWhenAddParticipantToGroup_IfParticipantIsAlreadyOnGroup() throws JSONException {
                var response = given()
                                .port(port)
                                .body(new JSONObject().put("name", "Carlos").toString())
                                .header("Content-Type", "application/json")
                                .when()
                                .post("/v1/group/1000/participant");

                response.then()
                                .assertThat().statusCode(400)
                                .body("success", equalTo(false))
                                .body("message", equalTo("Participant with name Carlos already exists"));
        }

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
