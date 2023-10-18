package com.demoqa.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.tests.TestData.login;
import static com.demoqa.tests.TestData.password;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;

public class CollectionTests extends TestBase {
    @Test
    void addBookToCollectionTest() {
        String authData = "{\"userName\":\"test1234560\",\"password\":\"Test123456@\"}";

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

        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"cf1ca36c-551e-4db0-9768-35ea94748c81\",\"collectionOfIsbns\":[{\"isbn\":\"9781449325862\"}]}",
                authResponse.path("userId") , isbn);

        Response addBookResponse = given()
               //в спецификацию убрать ниже
                .log().uri()
                .log().method()
                .log().body()
                //в спецификацию убрать выше
                .contentType(JSON)
                .headers("", authResponse.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().response();

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("/profile");
        $("#userName-value").shouldHave(text(login));
        }

}
