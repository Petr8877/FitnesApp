package SingVersion.FitnesApp.util.converter.product;

import SingVersion.FitnesApp.core.dto.product.IngredientCPFCDto;
import SingVersion.FitnesApp.core.dto.product.SaveIngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class IngredientCPFCDtoToSaveDto implements Converter<IngredientCPFCDto, SaveIngredientDto> {
    @Override
    public SaveIngredientDto convert(IngredientCPFCDto source) {
        return new SaveIngredientDto(source.product(),
                                     source.weight(),
                                     source.calories(),
                                     BigDecimal.valueOf(source.proteins()).setScale(2, RoundingMode.FLOOR),
                                     BigDecimal.valueOf(source.fats()).setScale(2, RoundingMode.FLOOR),
                                     BigDecimal.valueOf(source.carbohydrates()).setScale(2, RoundingMode.FLOOR));
    }
}
