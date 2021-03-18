package com.library.batch.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.batch.beans.BookBean;
import com.library.batch.beans.EmpruntBean;
import com.library.batch.beans.ExemplaireBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class ExemplaireService {

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    @Autowired
    SecurityService securityService;

    @Autowired
    EmpruntService empruntService;

    @Autowired
    MyRequestFactory myRequestFactory;

    public ExemplaireBean getExemplaireByBarcode(String barcode){

        RestTemplate restTemplate = myRequestFactory.getRestTemplate();

        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        String url = applicationConfiguration.getApiBookBaseUrl() +"exemplaires/search/byBarcode?barcode=" + barcode + "&projection=withBook";


        ResponseEntity<ExemplaireBean> response = restTemplate.exchange(url, HttpMethod.GET, request, ExemplaireBean.class);



        return response.getBody();
    }

    public Boolean isExemplaireDispo(int barcode) throws JsonProcessingException {

        Collection<EmpruntBean> emprunts = empruntService.getEmprunts();

        if (emprunts.isEmpty()) return true;
        Boolean returnValue = true;
        for (EmpruntBean emprunt : emprunts) {
            if (emprunt.getDateRetour() == null) {
                returnValue = false;
            }

        }
        return returnValue;
    }

    public List<ExemplaireBean> getExemplaireByBookId(int bookId) throws JsonProcessingException {
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        String url = applicationConfiguration.getApiBookBaseUrl() + "exemplaires/search/byBookId?bookId=" + bookId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        String data = response.getBody();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsNode = om.readTree(data);
        String test = jsNode.at("/_embedded/exemplaires").toString();
        return om.readValue(test, new TypeReference<List<ExemplaireBean>>() {
        });

    }

    public BookBean getBookById(int bookId){
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();

        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        String url = applicationConfiguration.getApiBookBaseUrl() +"books/" + bookId;


        ResponseEntity<BookBean> response = restTemplate.exchange(url, HttpMethod.GET, request, BookBean.class);



        return response.getBody();
    }
}
