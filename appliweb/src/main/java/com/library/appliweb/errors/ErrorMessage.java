package com.library.appliweb.errors;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessage {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
