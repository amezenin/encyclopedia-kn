package com.knits.product.exceptions;

public class UserException extends AppException{

    public UserException(String message){
        super(message);
    }

    public UserException(String message,  int code){
        super(message);
        setCode(code);
    }

    public UserException(Exception e){
        super(e);
    }
}
