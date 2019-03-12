package ch.uzh.ifi.seal.soprafs19.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) //code 409
public class DuplicateException extends RuntimeException{
    public DuplicateException(String exception) {
        super(exception);
    }
}