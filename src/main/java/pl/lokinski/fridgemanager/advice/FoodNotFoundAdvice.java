package pl.lokinski.fridgemanager.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lokinski.fridgemanager.exception.FoodNotFoundException;

@ControllerAdvice
public class FoodNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String foodNotFoundHandler(FoodNotFoundException exception) {
        return exception.getMessage();
    }
}