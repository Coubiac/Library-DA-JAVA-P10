package com.library.batch.beans;

import lombok.Data;

@Data
public class UserBean {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;

}
