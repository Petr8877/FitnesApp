package SingVersion.FitnesApp.core.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.util.UUID;

public class SaveProductDto extends ProductDto {

    @NonNull
    private final UUID uuid;
    @NotEmpty
    @JsonProperty("dt_create")
    private final long dtCreate;
    @NotEmpty
    @JsonProperty("dt_update")
    private final long dtUpdate;

    public SaveProductDto(String title, int weight, int calories, double proteins, double fats,
                          double carbohydrates, @NonNull UUID uuid, @NotEmpty long dtCreate, @NotEmpty long dtUpdate) {
        super(title, weight, calories, proteins, fats, carbohydrates);
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    @NonNull
    public UUID getUuid() {
        return uuid;
    }

    @NotEmpty
    public long getDtCreate() {
        return dtCreate;
    }

    @NotEmpty
    public long getDtUpdate() {
        return dtUpdate;
    }
}
