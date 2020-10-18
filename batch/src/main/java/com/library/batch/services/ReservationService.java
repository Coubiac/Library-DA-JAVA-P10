package com.library.batch.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.batch.beans.ReservationBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    MyRequestFactory myRequestFactory;
    @Autowired
    SecurityService securityService;
    @Autowired
    ApplicationConfiguration applicationConfiguration;

    public List<ReservationBean> getNextReservations() throws JsonProcessingException {
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        String url = applicationConfiguration.getApiReservationBaseUrl() + "search/byNextUser";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String data = response.getBody();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsNode = om.readTree(data);
        String content = jsNode.at("/_embedded/reservationEntities").toString();
        return om.readValue(content, new TypeReference<List<ReservationBean>>() {
        });

    }


}
