package com.natwest.prime.service.exception;

public class NumberNotInRangeException extends RuntimeException{

    public NumberNotInRangeException(String message){
        super(message);
    }
}
