package com.library.users.model.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UpdateUserDetailsRequestModel {
    @NotNull
    @Size(min =2)
    private String firstName;

    @Size(min = 2)
    private String lastName;

    @Size(min = 2)
    private String address;
    @Pattern(regexp="[\\d]{5}", message="Must be 5 digits")
    private String postalCode;
    @Size(min = 2)
    private String city;
}
