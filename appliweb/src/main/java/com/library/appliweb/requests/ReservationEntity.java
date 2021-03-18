package com.library.appliweb.requests;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationEntity {

    private int id;
    private int bookId;
    private String userId;
    private Date dateReservation;
    private boolean active;
    private Date dateActive;

}
