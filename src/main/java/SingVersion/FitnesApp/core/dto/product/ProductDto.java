package SingVersion.FitnesApp.core.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductDto {

    @NotEmpty
    private final String title;
    @Positive
    private final int weight;
    @PositiveOrZero
    private final int calories;
    @PositiveOrZero
    private final double proteins;
    @PositiveOrZero
    private final double fats;
    @PositiveOrZero
    private final double carbohydrates;

    public ProductDto(String title, int weight, int calories, double proteins, double fats, double carbohydrates) {
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public String getTitle() {
        return title;
    }

    public int getWeight() {
        return weight;
    }

    public int getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }
}
