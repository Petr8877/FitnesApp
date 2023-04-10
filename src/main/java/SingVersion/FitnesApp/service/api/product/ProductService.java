package SingVersion.FitnesApp.service.api.product;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.product.ProductDto;
import SingVersion.FitnesApp.core.dto.product.SaveProductDto;
import SingVersion.FitnesApp.entity.Product;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductService {

    SaveProductDto addProduct(ProductDto productDTO);

    SaveProductDto updateProduct(UUID uuid, LocalDateTime dtUpdate, ProductDto productDTO);

    PageDto<SaveProductDto> getProductPage(Pageable pageable);
}
