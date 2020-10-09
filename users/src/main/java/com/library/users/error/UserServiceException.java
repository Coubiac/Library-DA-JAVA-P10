package com.library.users.error;


import java.io.Serializable;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 3156743245678932L;


    public UserServiceException(String message)
    {
        super(message);
    }


}
