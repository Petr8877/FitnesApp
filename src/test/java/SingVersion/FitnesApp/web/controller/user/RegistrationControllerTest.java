package SingVersion.FitnesApp.web.controller.user;

import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import SingVersion.FitnesApp.entity.User;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;

class RegistrationControllerTest {

    private static final String AUTH_REQ = """
            {
                "email": "admin@gmail.com",
                "password": "string"
            }""";

    private final static User USER_ENTITY = new User(
            UUID.fromString("15450190-935f-42fa-91a4-e8c27894383c"),
            "kasperoyyyvich2petr@gmail.com",
            "Test Test",
            "password",
            LocalDateTime.now(),
            LocalDateTime.now(),
            Role.USER,
            Status.WAITING_ACTIVATION);


    @Test
    void registration() {

        String registrationReq = "{\n" +
                "  \"email\": \"" + Math.random() * 100 + "kasperovichpetr@gmail.com\",\n" +
                "  \"fio\": \"string\",\n" +
                "  \"password\": \"string\"\n" +
                "}";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(registrationReq)
                .post("http://localhost:8080/users/registration")
                .then()
                .statusCode(200);
    }

    @Test
    void verification() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .get("http://localhost:8080/users/verification?code="
                        + USER_ENTITY.getUuid() + "&email=" + USER_ENTITY.getEmail())
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void login() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(AUTH_REQ)
                .post("http://localhost:8080/users/login")
                .then()
                .statusCode(200)
                .extract().asString();
    }

    @Test
    void aboutMe() {
        String token = given()
                .when()
                .contentType(ContentType.JSON)
                .body(AUTH_REQ)
                .post("http://localhost:8080/users/login")
                .then()
                .statusCode(200)
                .extract().asString();

        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .get("http://localhost:8080/users/me")
                .then()
                .statusCode(200);
    }
}