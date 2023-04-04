package SingVersion.FitnesApp.core.dto.product;

import java.util.UUID;

public record IngredientCPFCDto(UUID product,
                                int weight,
                                int calories,
                                double proteins,
                                double fats,
                                double carbohydrates) {
}
