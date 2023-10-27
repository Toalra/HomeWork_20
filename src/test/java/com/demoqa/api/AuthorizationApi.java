package com.demoqa.api;

import com.demoqa.models.CredentialsModel;
import com.demoqa.models.LoginResponseModel;

import static com.demoqa.spec.LoginSpec.loginRequestSpec;
import static com.demoqa.spec.LoginSpec.loginResponseSpec;
import static io.restassured.RestAssured.given;

public class AuthorizationApi {
    public LoginResponseModel login(CredentialsModel credentials) {
        return given(loginRequestSpec)
                .body(credentials)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(loginResponseSpec)
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }
}
