package flight.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdviser {

    @ExceptionHandler
    public String valueNotExist(NoSuchElementException e){
        return "The request is wrong!\n"+e.getMessage();
    }

    @ExceptionHandler
    public String notAddRequest(IllegalArgumentException e){
        return "The request is wrong!\n"+e.getMessage();
    }

}
