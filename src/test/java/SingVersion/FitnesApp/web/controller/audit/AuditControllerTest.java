package SingVersion.FitnesApp.web.controller.audit;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class AuditControllerTest {

    private static final String token;

    static {
        String authReq = """
                {
                    "email": "admin@gmail.com",
                    "password": "string"
                }""";

        token = "Bearer " + given()
                .when()
                .contentType(ContentType.JSON)
                .body(authReq)
                .post("http://localhost:8080/users/login")
                .then()
                .statusCode(200)
                .extract().asString();
    }

    @Test
    void getPage() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/audit")
                .then()
                .statusCode(200);
    }

    @Test
    void getById() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/audit/c0374671-384d-457c-8c8f-ab0b78952fe2")
                .then()
                .statusCode(200);
    }
}