package SingVersion.FitnesApp.util.converter.product;

import SingVersion.FitnesApp.core.dto.product.ProductDto;
import SingVersion.FitnesApp.entity.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class ProductDtoToEntity implements Converter<ProductDto, Product> {
    @Override
    public Product convert(ProductDto source) {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        return new Product(UUID.randomUUID(),
                           time,
                           time,
                           source.getTitle(),
                           source.getWeight(),
                           source.getCalories(),
                           source.getProteins(),
                           source.getFats(),
                           source.getCarbohydrates());
    }
}
