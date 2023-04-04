package SingVersion.FitnesApp.core.dto.product;

import org.springframework.data.domain.Page;

import java.util.List;

public record ConvertPageDto<T, S>(Page<T> allPage,
                                   List<S> content) {
}
