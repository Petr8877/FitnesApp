package SingVersion.FitnesApp.util.converter.product;

import SingVersion.FitnesApp.core.dto.product.IngredientDto;
import SingVersion.FitnesApp.core.dto.product.RecipeDto;
import SingVersion.FitnesApp.entity.Ingredient;
import SingVersion.FitnesApp.entity.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RecipeDtoToEntity implements Converter<RecipeDto, Recipe> {
    @Override
    public Recipe convert(RecipeDto source) {
        LocalDateTime dtCreate = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        List<Ingredient> ingredientEntities = new ArrayList<>();
        for (IngredientDto ingredientDto : source.composition()) {
            ingredientEntities.add(new Ingredient(ingredientDto.product(), ingredientDto.weight()));
        }
        return new Recipe(UUID.randomUUID(),
                          dtCreate,
                          dtCreate,
                          source.title(),
                          ingredientEntities);
    }
}
