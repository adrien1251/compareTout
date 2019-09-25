package com.eltae.compareTout.exceptionHandler;

import com.eltae.compareTout.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.UnexpectedTypeException;

public class ExceptionCatcher {

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<Object> notFoundException(NotFoundException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public final ResponseEntity<Object> userNameNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity.status(422).body(ex.getMessage());
    }
//A faire generic
//    @ExceptionHandler({BadCredentialException.class})
//    public final ResponseEntity<Object> badCredentialException(BadCredentialException ex){
//        return ResponseEntity.status(401).body(ex.getMessage());
//    }

    @ExceptionHandler({UnexpectedTypeException.class})
    public final ResponseEntity<Object> BadArgumentException(UnexpectedTypeException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> allException(Exception ex){
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}
