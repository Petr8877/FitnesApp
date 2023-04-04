package SingVersion.FitnesApp.service.api.product;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.product.RecipeDto;
import SingVersion.FitnesApp.core.dto.product.SaveRecipeDto;
import SingVersion.FitnesApp.entity.Recipe;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecipeService {

    Recipe createRecipe(RecipeDto recipeDTO);

    PageDto<SaveRecipeDto> getRecipePage(Pageable pageable);

    Recipe updateRecipe(UUID uuid, LocalDateTime dtUpdate, RecipeDto recipeDTO);
}
