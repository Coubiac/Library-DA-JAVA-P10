package com.library.appliweb.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationPostRequest {
    private int bookId;
    private String userId;
}
