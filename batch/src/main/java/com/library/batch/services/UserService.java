package com.library.batch.services;


import com.library.batch.beans.UserBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserService {

    @Autowired
    SecurityService securityService;

    @Autowired
    MyRequestFactory myRequestFactory;

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    public UserBean getUserById(String userId){

        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(applicationConfiguration.getApiUserBaseUrl() + userId);
        ResponseEntity<UserBean> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET,
                request,
                UserBean.class);

        UserBean theUser = response.getBody();
        return theUser;
    }
}
