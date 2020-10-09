package com.library.users.model.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDetailsRequestModel {

    @NotNull
    @Size(min =2)
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull(message = "email cannot be null")
    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min=8, max=16, message = "The password must be between 8 and 16 characters long")
    private String password;


    @Size(min = 2, message="This address is not correct.")
    private String address;

    @Pattern(regexp="[\\d]{5}", message="Must be 5 digits !")
    private String postalCode;

    @Size(min = 2, message = "The French commune with the shortest name is called Y. You don't seem to live in this commune.")
    private String city;
}
