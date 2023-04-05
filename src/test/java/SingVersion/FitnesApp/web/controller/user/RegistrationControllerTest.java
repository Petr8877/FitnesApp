package SingVersion.FitnesApp.web.controller.user;

import SingVersion.FitnesApp.core.enums.Role;
import SingVersion.FitnesApp.core.enums.Status;
import SingVersion.FitnesApp.entity.User;
import SingVersion.FitnesApp.restAssured.Specification;
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


    @Test
    void registration() {

        String registrationReq = """
                {
                  "email": "%s",
                  "fio": "string",
                  "password": "string"
                }""".formatted(Math.random() * 100 + "kasperovichpetr@gmail.com");

        Specification.installSpecification(
                Specification.requestSpecification(registrationReq), Specification.responseSpecification200()
        );

        given()
                .when()
                .post("users/registration")
                .then();
    }

    @Test
    void verification() {
        Specification.installSpecification(
                Specification.requestSpecification(), Specification.responseSpecification400()
        );

        User user = new User(
                UUID.fromString("15450190-935f-42fa-91a4-e8c27894383c"),
                "kasperoyyyvich2petr@gmail.com",
                "Test Test",
                "password",
                LocalDateTime.now(),
                LocalDateTime.now(),
                Role.USER,
                Status.WAITING_ACTIVATION);

        given()
                .when()
                .get("users/verification?code=" + user.getUuid() + "&email=" + user.getEmail())
                .then();
    }

    @Test
    void login() {
        Specification.installSpecification(
                Specification.requestSpecification(AUTH_REQ), Specification.responseSpecification200()
        );

        given()
                .when()
                .post("users/login")
                .then();
    }

    @Test
    void aboutMe() {
        Specification.installSpecification(
                Specification.requestSpecification(AUTH_REQ), Specification.responseSpecification200()
        );

        given()
                .when()
                .get("users/me")
                .then();
    }
}