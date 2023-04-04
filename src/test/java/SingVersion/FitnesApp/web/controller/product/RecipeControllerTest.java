package SingVersion.FitnesApp.web.controller.product;

import SingVersion.FitnesApp.core.dto.product.SaveRecipeDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class RecipeControllerTest {

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
    void addRecipe() {
        String recipeBody = """
                {
                  "title": "%s",
                  "composition": [
                    {
                      "product": "554caf06-64e5-47ff-bcae-b37246dc3d24",
                      "weight": 150
                    },
                    {
                      "product": "f13222f8-3c11-41c2-977a-49653df1da50",
                      "weight": 200
                    }
                  ]
                }""".formatted("recipe" + Math.random() * 100);


        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(recipeBody)
                .post("http://localhost:8080/recipe")
                .then()
                .statusCode(200);
    }

    @Test
    void getRecipePage() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/recipe")
                .then()
                .statusCode(200);
    }

    @Test
    void updateRecipe() {
        String changeBody = """
                {
                  "title": "%s",
                  "composition": [
                    {
                      "product": "554caf06-64e5-47ff-bcae-b37246dc3d24",
                      "weight": 270
                    },
                    {
                        "product": "f55f1a44-49e2-45ab-a8bd-e2e91c5f5225",
                        "weight": 170
                    }
                  ]
                }""".formatted("Гречка с лисичками" + Math.random() * 100);

        List<SaveRecipeDto> list = given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get("http://localhost:8080/recipe")
                .then()
                .extract().body().jsonPath().getList("content", SaveRecipeDto.class);

        String recipeUUID = list.get(1).uuid().toString();
        String recipeVersion = String.valueOf(list.get(1).dtUpdate());

        given()
                .when()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(changeBody)
                .put("http://localhost:8080/recipe/" + recipeUUID + "/dt_update/" + recipeVersion)
                .then().log().all()
                .statusCode(200);
    }
}