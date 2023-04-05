package SingVersion.FitnesApp.web.controller.product;

import SingVersion.FitnesApp.core.dto.product.SaveProductDto;
import SingVersion.FitnesApp.restAssured.Specification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ProductControllerTest {


    @Test
    void addProduct() {
        String productBody = """
                {
                  "title": "%s",
                  "weight": 100,
                  "calories": 708,
                  "proteins": 5.6,
                  "fats": 1.0,
                  "carbohydrates": 70.1
                }""".formatted("product" + Math.random() * 100);

        Specification.installSpecification(
                Specification.requestSpecification(productBody), Specification.responseSpecification200()
        );

        given()
                .when()
                .post("product")
                .then();
    }

    @Test
    void updateProduct() {
        String updateProduct = """
                {
                  "title": "%s",
                  "weight": 100,
                  "calories": 308,
                  "proteins": 12.6,
                  "fats": 3.3,
                  "carbohydrates": 57.1
                }""".formatted("new product" + Math.random() * 100);

        Specification.installSpecification(
                Specification.requestSpecification(updateProduct), Specification.responseSpecification200()
        );

        List<SaveProductDto> productList = given()
                .when()
                .get("product")
                .then()
                .extract().body().jsonPath().getList("content", SaveProductDto.class);

        String productUUID = productList.get(1).getUuid().toString();
        String productVersion = String.valueOf(productList.get(1).getDtUpdate());

        given()
                .when()
                .put("product/" + productUUID + "/dt_update/" + productVersion)
                .then();
    }

    @Test
    void getProductPage() {
        given()
                .when()
                .get("product")
                .then();
    }
}