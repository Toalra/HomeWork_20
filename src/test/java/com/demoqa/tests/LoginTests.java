package com.demoqa.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.demoqa.tests.TestData.login;
import static com.demoqa.tests.TestData.password;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class LoginTests extends TestBase {
    @Test
    void successfulLoginWithUiTest() {
        open("/login");
        $("#userName").setValue(login);
        $("#password").setValue(password).pressEnter();
        $("#userName-value").shouldHave(text(login));
    }

    @Test
    void successfulLoginWithApiTest() {
        String authData = "{\"userName\": \"test1234560\", \"password\": \"Test123456@\": }";

        Response authResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        open(" /profile");
        $("#userName-value").shouldHave(text(login));
        }

}
