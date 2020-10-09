package com.library.appliweb.proxies;


import com.library.appliweb.beans.LoginFormBean;
import feign.Headers;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "users")
public interface UserProxy {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PostMapping("/users/users/login")
    ResponseEntity<Void> getToken(@RequestBody LoginFormBean loginFormBean);


}
