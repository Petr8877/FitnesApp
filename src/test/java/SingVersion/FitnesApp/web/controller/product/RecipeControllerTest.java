package SingVersion.FitnesApp.web.controller.product;

import SingVersion.FitnesApp.core.dto.product.SaveRecipeDto;
import SingVersion.FitnesApp.restAssured.Specification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

class RecipeControllerTest {


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

        Specification.installSpecification(
                Specification.requestSpecification(recipeBody), Specification.responseSpecification200()
        );

        given()
                .when()
                .post("recipe")
                .then();
    }

    @Test
    void getRecipePage() {
        Specification.installSpecification(
                Specification.requestSpecification(), Specification.responseSpecification200()
        );

        given()
                .when()
                .get("recipe")
                .then();
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

        Specification.installSpecification(
                Specification.requestSpecification(changeBody), Specification.responseSpecification200()
        );

        List<SaveRecipeDto> list = given()
                .when()
                .get("recipe")
                .then()
                .extract().body().jsonPath().getList("content", SaveRecipeDto.class);

        String recipeUUID = list.get(1).uuid().toString();
        String recipeVersion = String.valueOf(list.get(1).dtUpdate());

        given()
                .when()
                .put("recipe/" + recipeUUID + "/dt_update/" + recipeVersion)
                .then();
    }
}