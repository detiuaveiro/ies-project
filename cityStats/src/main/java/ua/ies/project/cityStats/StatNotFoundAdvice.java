package ua.ies.project.cityStats;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StatNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(StatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String statNotFoundHandler(StatNotFoundException e){
        return e.getMessage();
    }
}
