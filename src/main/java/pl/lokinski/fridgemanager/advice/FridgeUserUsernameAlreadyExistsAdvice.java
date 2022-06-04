package pl.lokinski.fridgemanager.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lokinski.fridgemanager.exception.FridgeUserUsernameAlreadyExistsException;

@ControllerAdvice
public class FridgeUserUsernameAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(FridgeUserUsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String fridgeUserUsernameAlreadyExists(FridgeUserUsernameAlreadyExistsException exception) {
        return exception.getMessage();
    }
}
