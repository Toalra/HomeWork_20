package com.demoqa.spec;

import com.demoqa.tests.TestBase;
import io.restassured.specification.RequestSpecification;
import static com.demoqa.helpers.CustomAllureListener.withCustomTemplates;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class BookSpecs extends TestBase {
    public static RequestSpecification requestBookSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON);

}
