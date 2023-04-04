package SingVersion.FitnesApp.core.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record SaveRecipeDto(@NonNull UUID uuid,
                            @NotEmpty @JsonProperty("dt_create") long dtCreate,
                            @NotEmpty @JsonProperty("dt_update") long dtUpdate,
                            @NotEmpty String title,
                            @NonNull List<SaveIngredientDto> composition,
                            @NotEmpty int weight,
                            @NotEmpty int calories,
                            @NotEmpty BigDecimal proteins,
                            @NotEmpty BigDecimal fats,
                            @NotEmpty BigDecimal carbohydrates
                            ) {
}
