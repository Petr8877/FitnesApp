package SingVersion.FitnesApp.core.dto.exception;


import SingVersion.FitnesApp.core.exception.MyError;

import java.util.List;

public record MultipleErrorResponseDto(String logref,
                                       List<MyError> errors) {

}
