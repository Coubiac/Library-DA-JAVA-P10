package com.library.users.shared;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -95418554424483647L;

    private String userId;

    @NotNull
    @Size(min =2)
    private String firstName;
    @NotNull
    @Size(min =2)
    private String lastName;
    @NotNull(message = "email cannot be null")
    @Email
    private String email;
    @NotNull(message = "password cannot be null")
    @Size(min=8, max=16)
    private String password;
    private String encryptedPassword;
    @Size(min = 2)
    private String address;
    @Pattern(regexp="[\\d]{5}", message="Must be 5 digits")
    private String postalCode;
    @Size(min = 2)
    private String city;
}
