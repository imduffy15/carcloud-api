package ie.ianduffy.carcloud.web.exception;

import ie.ianduffy.carcloud.web.exception.dto.ErrorDTO;
import ie.ianduffy.carcloud.web.exception.dto.FieldErrorDTO;
import ie.ianduffy.carcloud.web.exception.dto.GenericErrorDTO;

import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorDTO handleException(MethodArgumentNotValidException ex) {
        List<FieldErrorDTO> fieldErrors = new LinkedList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors
                .add(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new ErrorDTO(fieldErrors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    ErrorDTO handleException(HttpMediaTypeNotSupportedException ex) {
        return new ErrorDTO(new GenericErrorDTO(ex.getClass().toString(), ex.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorDTO handleException(HttpMessageNotReadableException ex) {
        return new ErrorDTO(new GenericErrorDTO(ex.getClass().toString(), ex.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorDTO handleException(StaleStateException ex) {
        return new ErrorDTO(new GenericErrorDTO(ex.getClass().toString(), ex.getMessage()));
    }
}
