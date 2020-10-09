package com.library.users.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 95457834664647L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String city;

}
