package SingVersion.FitnesApp.core.dto.product;



import SingVersion.FitnesApp.entity.Recipe;

import java.util.List;

public record RecipeConvertDto(List<SaveIngredientDto> ingredient,
                               Recipe recipeEntity,
                               RecipeCPFCDto recipeCPFCDto) {
}
