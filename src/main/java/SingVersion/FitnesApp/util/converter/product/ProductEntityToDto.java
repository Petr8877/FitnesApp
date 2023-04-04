package SingVersion.FitnesApp.util.converter.product;

import SingVersion.FitnesApp.core.dto.product.SaveProductDto;
import SingVersion.FitnesApp.entity.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ProductEntityToDto implements Converter<Product, SaveProductDto> {
    @Override
    public SaveProductDto convert(Product source) {
        return new SaveProductDto(source.getTitle(),
                                  source.getWeight(),
                                  source.getCalories(),
                                  source.getProteins(),
                                  source.getFats(),
                                  source.getCarbohydrates(),
                                  source.getUuid(),
                                  ZonedDateTime.of(source.getDtCreate(), ZoneId.systemDefault()).toInstant().toEpochMilli(),
                                  ZonedDateTime.of(source.getDtUpdate(), ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
