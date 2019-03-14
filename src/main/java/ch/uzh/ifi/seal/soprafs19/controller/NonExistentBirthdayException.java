package ch.uzh.ifi.seal.soprafs19.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //error 404
public class NonExistentBirthdayException extends RuntimeException{
    public NonExistentBirthdayException(String exception) {
        super(exception);
    }
}