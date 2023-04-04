package SingVersion.FitnesApp.web.exception;

import SingVersion.FitnesApp.core.dto.exception.MultipleErrorResponseDto;
import SingVersion.FitnesApp.core.dto.exception.SingleErrorResponseDto;
import SingVersion.FitnesApp.core.exception.MyError;
import SingVersion.FitnesApp.core.exception.SingleErrorResponse;
import jakarta.persistence.NonUniqueResultException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler({
            ServletException.class, SingleErrorResponse.class, NonUniqueResultException.class,
            UsernameNotFoundException.class, ConnectException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class
    })
    public ResponseEntity<SingleErrorResponseDto> catchSingleErrorResponse(Exception e) {
        String message = e.getMessage();
        if (e.getClass().equals(NumberFormatException.class) || e.getClass().equals(MethodArgumentTypeMismatchException.class)) {
            message = "Check the code you entered is correct";
        }
        if (e.getClass().equals(HttpMessageNotReadableException.class)) {
            message = "Wrong form, check all fields";
        }
        if (e.getClass().equals(ConnectException.class) || e.getClass().equals(HttpMessageNotReadableException.class)){
            return ResponseEntity.status(500).body(new SingleErrorResponseDto("Проблема сервера ", message));
        }
        return ResponseEntity.status(400).body(new SingleErrorResponseDto("Не верный запрос ", message));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultipleErrorResponseDto catchMultipleErrorResponse(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        ArrayList<MyError> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new MyError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new MultipleErrorResponseDto("Прроверьте верность внесенных данных", errors);
    }
}
