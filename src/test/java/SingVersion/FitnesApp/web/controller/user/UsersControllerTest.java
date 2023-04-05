package SingVersion.FitnesApp.web.controller.user;

import SingVersion.FitnesApp.restAssured.Specification;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

class UsersControllerTest {


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

        Specification.installSpecification(
                Specification.requestSpecification(createReq), Specification.responseSpecification200()
        );

        given()
                .when()
                .post("users")
                .then();
    }

    @Test
    void getUsersPage() {
        Specification.installSpecification(
                Specification.requestSpecification(), Specification.responseSpecification200()
        );

        given()
                .when()
                .get("users")
                .then();
    }

    @Test
    void getUserById() {
        Specification.installSpecification(
                Specification.requestSpecification(), Specification.responseSpecification200()
        );

        given()
                .when()
                .get("users/8ef84e6d-e12b-4590-a829-a9548d5e11b5")
                .then();
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

        Specification.installSpecification(
                Specification.requestSpecification(changeBody), Specification.responseSpecification200()
        );

        String version = given()
                .when()
                .get("users/8ef84e6d-e12b-4590-a829-a9548d5e11b5")
                .then()
                .extract().jsonPath().getString("dt_update");

        given()
                .when()
                .put("users/8ef84e6d-e12b-4590-a829-a9548d5e11b5/dt_update/" + version)
                .then();
    }
}