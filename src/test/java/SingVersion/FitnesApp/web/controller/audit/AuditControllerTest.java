package SingVersion.FitnesApp.web.controller.audit;

import SingVersion.FitnesApp.restAssured.Specification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class AuditControllerTest {


    @Test
    void getPage() {
        Specification.installSpecification(
                Specification.requestSpecification(), Specification.responseSpecification200()
        );

        given()
                .when()
                .get("audit")
                .then();
    }

    @Test
    void getById() {
        Specification.installSpecification(
                Specification.requestSpecification(), Specification.responseSpecification200()
        );

        given()
                .when()
                .get("audit/c0374671-384d-457c-8c8f-ab0b78952fe2")
                .then();
    }
}