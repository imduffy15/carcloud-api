package ie.ianduffy.carcloud.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

/**
 * REST exception handlers defined at a global level for the application
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler
    public void handleException(MethodArgumentNotValidException e, HttpServletResponse response)
        throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    public void handleException(JpaSystemException e, HttpServletResponse response)
        throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    void handleException(EntityNotFoundException e, HttpServletResponse response)
        throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler
    void handleException(EntityExistsException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }
}
