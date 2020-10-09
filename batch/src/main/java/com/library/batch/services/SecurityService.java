package com.library.batch.services;


import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;


@Service
public class SecurityService {


    @Autowired
    ApplicationConfiguration applicationConfiguration;

    @Autowired
    MyRequestFactory myRequestFactory;



    private String getToken(String url, String username, String password){


        String body = "{" +
                "\"email\": \"" + username + "\"," +
                "\"password\": \"" + password + "\"" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST,
                request,
                String.class);
        return response.getHeaders().getFirst("token");
    }

    public HttpHeaders authenticate(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(getToken(applicationConfiguration.getApiUserBaseUrl()+"login", applicationConfiguration.getApiUser(), applicationConfiguration.getApiPassword()));
        return headers;

    }


}
