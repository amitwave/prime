package com.natwest.prime.exception;

public class InvalidRequestException  extends RuntimeException{

    public  InvalidRequestException(String message){
        super(message);
    }
}
