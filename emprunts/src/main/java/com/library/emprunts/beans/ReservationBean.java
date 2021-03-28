package com.library.emprunts.beans;

import lombok.Data;
import java.util.Date;

@Data
public class ReservationBean {

    private Long id;
    private int bookId;
    private String userId;
    private Date dateReservation;
    private Boolean active;
    private Date dateActive;
}
