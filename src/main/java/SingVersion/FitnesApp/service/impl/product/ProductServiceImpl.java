package SingVersion.FitnesApp.service.impl.product;

import SingVersion.FitnesApp.core.dto.DetailsDto;
import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.CreateEntryDto;
import SingVersion.FitnesApp.core.dto.product.ConvertPageDto;
import SingVersion.FitnesApp.core.dto.product.ProductDto;
import SingVersion.FitnesApp.core.dto.product.SaveProductDto;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import SingVersion.FitnesApp.entity.Product;
import SingVersion.FitnesApp.repository.ProductRepository;
import SingVersion.FitnesApp.service.api.audit.AuditService;
import SingVersion.FitnesApp.service.api.product.ProductService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ConversionService conversionService;
    private final AuditService auditService;

    public ProductServiceImpl(ProductRepository repository, ConversionService conversionService,
                              AuditService auditService) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.auditService = auditService;
    }

    @Override
    @Transactional
    public SaveProductDto addProduct(ProductDto productDTO) {
        if (!repository.existsByTitle(productDTO.getTitle())) {
            Product product = repository.save(
                    Objects.requireNonNull(conversionService.convert(productDTO, Product.class))
            );
            toAudit("Создание нового продукта uuid:" + product.getUuid());
            return conversionService.convert(product, SaveProductDto.class);
        } else {
            throw new SingleErrorResponse("Продукт с таким наименованием уже существует");
        }
    }

    @Override
    @Transactional
    public SaveProductDto updateProduct(UUID uuid, LocalDateTime dtUpdate, ProductDto productDTO) {
        Product productById = repository.findById(uuid).orElseThrow(()
                -> new SingleErrorResponse("Продукта с таким ид не существует"));
        if (Objects.equals(dtUpdate, productById.getDtUpdate())) {
            productById.setTitle(productDTO.getTitle());
            productById.setWeight(productDTO.getWeight());
            productById.setCalories(productDTO.getCalories());
            productById.setProteins(productDTO.getProteins());
            productById.setFats(productDTO.getFats());
            productById.setCarbohydrates(productDTO.getCarbohydrates());
            toAudit("Изменение продукта uuid:" + productById.getUuid());
            return conversionService.convert(repository.save(productById), SaveProductDto.class);
        } else {
            throw new SingleErrorResponse("Не верная версия");
        }
    }

    @Override
    @ReadOnlyProperty
    public PageDto<SaveProductDto> getProductPage(Pageable pageable) {
        Page<Product> allPage = repository.findAllPage(pageable);
        List<SaveProductDto> content = new ArrayList<>();
        for (Product product : allPage) {
            content.add(conversionService.convert(product, SaveProductDto.class));
        }
        return conversionService.convert(new ConvertPageDto(allPage, content), PageDto.class);
    }

    private void toAudit(String text) {
        DetailsDto principal = (DetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        auditService.createEntry(new CreateEntryDto(principal.getUuid(), principal.getUsername(), principal.getFio(),
                principal.getRole(), text, 1));
    }
}
