package cz.muni.fi.hrm.exception.handling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(IllegalArgumentException e) {
        ApiError error = new ApiError(HttpStatus.valueOf(406), e.getMessage());
        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }
}