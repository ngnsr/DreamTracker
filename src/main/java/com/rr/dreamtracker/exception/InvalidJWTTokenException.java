package com.rr.dreamtracker.exception;

public class InvalidJWTTokenException extends RuntimeException{
    public InvalidJWTTokenException(String message){
        super(message);
    }
}
