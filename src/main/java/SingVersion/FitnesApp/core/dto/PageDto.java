package SingVersion.FitnesApp.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PageDto<T>(int number,
                         int size,
                         int totalPage,
                         @JsonProperty("total_elements") long totalElements,
                         boolean first,
                         @JsonProperty("number_of_elements") long numberOfElements,
                         boolean last,
                         List<T> content) {

}
