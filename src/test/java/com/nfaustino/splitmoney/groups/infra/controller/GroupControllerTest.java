package com.nfaustino.splitmoney.groups.infra.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.nfaustino.splitmoney.shared.infra.data.GroupData;
import com.nfaustino.splitmoney.shared.infra.data.ParticipantData;
import com.nfaustino.splitmoney.shared.infra.repositories.GroupRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    GroupRepository groupRepository;

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
    void should_AddParticipantToGroup() throws JSONException {
        var data = groupRepository.save(GroupData.builder().name("test").build());
        var response = given()
                .port(port)
                .body(new JSONObject().put("name", "Carlos").toString())
                .header("Content-Type", "application/json")
                .when()
                .post("/v1/group/%d/participant".formatted(data.getId()));

        response.then()
                .assertThat().statusCode(200)
                .body("success", equalTo(true))
                .body("message", emptyOrNullString());
    }

    @Test
    void should_ReturnErrorAddParticipantToGroup_IfBodyIsInvalid() throws JSONException {
        var data = groupRepository.save(GroupData.builder().name("test").build());
        var response = given()
                .port(port)
                .body(new JSONObject().put("wrong", "Carlos").toString())
                .header("Content-Type", "application/json")
                .when()
                .post("/v1/group/%d/participant".formatted(data.getId()));

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
                .post("/v1/group/%d/participant".formatted(1));

        response.then()
                .assertThat().statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Group with id 1 was not found"));
    }

    @Test
    void should_ReturnErrorWhenAddParticipantToGroup_IfParticipantIsAlreadyOnGroup() throws JSONException {
        var group = groupRepository.save(GroupData.builder()
                .name("test")
                .build());
        group.setParticipants(List.of(ParticipantData.builder().group(group).name("Carlos").build()));
        groupRepository.save(group);
        var response = given()
                .port(port)
                .body(new JSONObject().put("name", "Carlos").toString())
                .header("Content-Type", "application/json")
                .when()
                .post("/v1/group/%d/participant".formatted(group.getId()));

        response.then()
                .assertThat().statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Participant with name Carlos already exists"));
    }
}
