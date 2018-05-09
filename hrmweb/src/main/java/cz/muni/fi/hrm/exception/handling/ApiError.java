package cz.muni.fi.hrm.exception.handling;

import org.springframework.http.HttpStatus;

/**
 * this class is used as error envelope that is sent to frontend
 */
public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {

        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}