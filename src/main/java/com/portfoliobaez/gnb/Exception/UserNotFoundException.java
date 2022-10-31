package com.portfoliobaez.gnb.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User y/o contraseña incorrecto");
    }

}
