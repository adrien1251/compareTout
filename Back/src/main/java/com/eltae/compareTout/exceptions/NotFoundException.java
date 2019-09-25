package com.eltae.compareTout.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@Data
public class NotFoundException extends RuntimeException{
    private HttpStatus httpStatus;

    public NotFoundException(HttpStatus exceptionStatus, Long id) {
        super("Unable to find user with id " +  id);
        this.httpStatus = exceptionStatus;
        log.warn("Unable to find user with id {}", () -> id);
    }

    public NotFoundException(HttpStatus exceptionStatus, String name) {
        super("Unable to find user " +  name);
        this.httpStatus = exceptionStatus;
        log.warn("Unable to find user", () -> name);
    }
}
