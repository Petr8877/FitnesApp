package SingVersion.FitnesApp.web.controller.product;

import SingVersion.FitnesApp.core.dto.product.SaveProductDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class ProductControllerTest {

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

        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(productBody)
                .post("http://localhost:8080/product")
                .then()
                .statusCode(200);
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

        List<SaveProductDto> productList = given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/product")
                .then()
                .extract().body().jsonPath().getList("content", SaveProductDto.class);

        String productUUID = productList.get(1).getUuid().toString();
        String productVersion = String.valueOf(productList.get(1).getDtUpdate());

        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(updateProduct)
                .put("http://localhost:8080/product/" + productUUID + "/dt_update/" + productVersion)
                .then()
                .statusCode(200);
    }

    @Test
    void getProductPage() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/product")
                .then()
                .statusCode(200);
    }
}