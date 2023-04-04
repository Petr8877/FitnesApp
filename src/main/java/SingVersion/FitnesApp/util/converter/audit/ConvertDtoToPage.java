package SingVersion.FitnesApp.util.converter.audit;

import SingVersion.FitnesApp.core.dto.PageDto;
import SingVersion.FitnesApp.core.dto.audit.ConvertDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConvertDtoToPage implements Converter<ConvertDto, PageDto> {
    @Override
    public PageDto convert(ConvertDto source) {
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
