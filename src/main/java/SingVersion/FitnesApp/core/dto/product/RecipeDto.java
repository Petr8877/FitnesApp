package SingVersion.FitnesApp.core.dto.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RecipeDto(@NotEmpty String title,
                        @NotEmpty List<@Valid IngredientDto> composition) {

}
