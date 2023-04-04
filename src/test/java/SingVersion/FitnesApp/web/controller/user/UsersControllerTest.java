package SingVersion.FitnesApp.web.controller.user;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

class UsersControllerTest {

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
    void addNewUser() {
        String createReq = """
                {
                  "email": "123@qwe.sx",
                  "fio": "user1",
                  "role": "USER",
                  "status": "ACTIVATED",
                  "password": "string"
                }""";

        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(createReq)
                .post("http://localhost:8080/users")
                .then()
                .statusCode(200);
    }

    @Test
    void getUsersPage() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/users")
                .then()
                .statusCode(200);
    }

    @Test
    void getUserById() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/users/8ef84e6d-e12b-4590-a829-a9548d5e11b5")
                .then()
                .statusCode(200);
    }

    @Test
    void updateUser() {
        String changeBody = """
                {
                  "email": "change@com.com",
                  "fio": "new fio",
                  "role": "USER",
                  "status": "ACTIVATED",
                  "password": "string"
                }""";

        String version = given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/users/8ef84e6d-e12b-4590-a829-a9548d5e11b5")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("dt_update");

        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(changeBody)
                .put("http://localhost:8080/users/8ef84e6d-e12b-4590-a829-a9548d5e11b5/dt_update/" + version)
                .then()
                .statusCode(200);
    }
}