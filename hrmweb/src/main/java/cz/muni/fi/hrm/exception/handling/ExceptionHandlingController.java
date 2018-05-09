package cz.muni.fi.hrm.exception.handling;

import cz.muni.fi.hrm.service.ComputationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * this controller handles every illegal argument exception that is sent from service layer
 */
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(IllegalArgumentException e) {
        logger.error("problem occured and sent to frontend ",e.getMessage());
        ApiError error = new ApiError(HttpStatus.valueOf(406), e.getMessage());
        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }
}