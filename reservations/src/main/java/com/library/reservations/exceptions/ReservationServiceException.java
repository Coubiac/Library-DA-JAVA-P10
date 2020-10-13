package com.library.reservations.exceptions;

public class ReservationServiceException extends RuntimeException {
    private static final long serialVersionUID = 3156743245678932L;


    public ReservationServiceException(String message)
    {
        super(message);
    }


}
