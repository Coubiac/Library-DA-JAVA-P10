package com.library.appliweb.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationBean implements Comparable<ReservationBean> {

    private Integer id;
    private BookBean bookBean;
    private UserBean userBean;
    private Date dateReservation;
    private Boolean active;
    private Date dateActive;

    @Override
    public int compareTo(ReservationBean o) {
        if(getDateReservation() == null || o.getDateReservation() == null){
            return 0;
        }
        return getDateReservation().compareTo(o.getDateReservation());
    }
}
