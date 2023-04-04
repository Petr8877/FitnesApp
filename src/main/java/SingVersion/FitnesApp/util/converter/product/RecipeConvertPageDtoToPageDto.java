package SingVersion.FitnesApp.util.converter.product;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.product.ConvertPageDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeConvertPageDtoToPageDto implements Converter<ConvertPageDto, PageDto> {
    @Override
    public PageDto convert(ConvertPageDto source) {
        return new PageDto<>(source.allPage().getNumber(),
                             source.allPage().getSize(),
                             source.allPage().getTotalPages(),
                             source.allPage().getTotalElements(),
                             source.allPage().isFirst(),
                             source.allPage().getNumberOfElements(),
                             source.allPage().isLast(),
                             source.content());
    }
}