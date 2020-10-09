package com.library.batch.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.batch.beans.EmpruntBean;
import com.library.batch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmpruntService {
    @Autowired
    SecurityService securityService;

    @Autowired
    ApplicationConfiguration applicationConfiguration;


    @Autowired
    MyRequestFactory myRequestFactory;

    public List<EmpruntBean> getEmprunts() throws JsonProcessingException {

        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        String url = applicationConfiguration.getApiEmpruntBaseUrl() +"search/findEmpruntEntitiesByDateEmpruntBeforeAndDateRetourIsNull?dateEmprunt=" + getDateEmpruntAsString();


        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        //On convertit la réponse en liste d'emprunts
        String data = response.getBody();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsNode = om.readTree(data);
        String test = jsNode.at("/_embedded/empruntEntities").toString();
        List<EmpruntBean> empruntBeans = om.readValue(test, new TypeReference<List<EmpruntBean>>() {
        });
        for(EmpruntBean empruntBean:empruntBeans){
            Calendar c = Calendar.getInstance();
            c.setTime(empruntBean.getDateEmprunt());
            if(empruntBean.getIsExtended()){
                c.add(Calendar.DATE, applicationConfiguration.getJoursEmprunt() + applicationConfiguration.getJoursProlongation());
            }
            else {
                c.add(Calendar.DATE, applicationConfiguration.getJoursEmprunt());
            }
            empruntBean.setDateRetourPrevu(c.getTime());
        }



        return empruntBeans;



    }

    private String getDateEmpruntAsString(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -5);
        Date dateEmprunt = c.getTime();
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateEmpruntAsString = df.format(dateEmprunt);
        return dateEmpruntAsString;
    }

}
