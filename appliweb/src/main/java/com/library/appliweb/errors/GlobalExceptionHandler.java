package com.library.appliweb.errors;

import feign.FeignException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public String handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        if (e.status() == 401) {
            return "Feign Error : " + e.status() + " - Authentication failed: bad credentials";
        }
        if (e.status() == 403) {
            return "Feign Error : " + e.status() + " - Access Denied";
        }

        return "Feign Error :" + e.status() + " - " + e.getMessage();
    }

}