package SingVersion.FitnesApp.util.converter.product;

import SingVersion.FitnesApp.core.dto.product.IngredientDto;
import SingVersion.FitnesApp.entity.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientDtoToEntity implements Converter<IngredientDto, Ingredient> {
    @Override
    public Ingredient convert(IngredientDto source) {
        return new Ingredient(source.product(),
                              source.weight());
    }
}
