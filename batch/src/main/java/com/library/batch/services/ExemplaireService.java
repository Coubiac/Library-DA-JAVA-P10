package com.library.batch.services;

import com.library.batch.beans.ExemplaireBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExemplaireService {

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    @Autowired
    SecurityService securityService;

    @Autowired
    MyRequestFactory myRequestFactory;

    public ExemplaireBean getExemplaireByBarcode(String barcode){

        RestTemplate restTemplate = myRequestFactory.getRestTemplate();

        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        String url = applicationConfiguration.getApiBookBaseUrl() +"exemplaires/search/byBarcode?barcode=" + barcode + "&projection=withBook";


        ResponseEntity<ExemplaireBean> response = restTemplate.exchange(url, HttpMethod.GET, request, ExemplaireBean.class);



        return response.getBody();
    }
}
