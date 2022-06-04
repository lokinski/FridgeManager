package pl.lokinski.fridgemanager.advice;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BadRequestAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badPropertyHandler() {
        return "Could not process the request";
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class, NullPointerException.class })
    protected ResponseEntity<Object> runtimeExceptionHandler(RuntimeException exception, WebRequest request) {
        return this.handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
