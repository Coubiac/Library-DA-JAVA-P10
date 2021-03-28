package com.library.batch.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.batch.beans.ReservationBean;
import com.library.batch.configuration.ApplicationConfiguration;
import com.library.batch.schedulingtasks.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    MyRequestFactory myRequestFactory;
    @Autowired
    SecurityService securityService;
    @Autowired
    ApplicationConfiguration applicationConfiguration;

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    /**
     * Cette méthode effectue une requete HTTP vers l'API reservation et récupère, pour chaque livre reservé,
     * la reservation dont la date est la plus ancienne.
     * @return Une liste de reservations.
     * @throws JsonProcessingException
     */
    public List<ReservationBean> getNextReservations() throws JsonProcessingException {
        String url = applicationConfiguration.getApiReservationBaseUrl() + "search/byNextUser";
        return callGet(url);
    }


    /**
     * Cette methode effectue une requete HTTP vers l'API reservation et récupère toutes les reservations actives dont
     * la date limite est dépassée. La date limite est liée à la propriété "batch.activeReservationTime"
     * @return une liste de reservations
     * @throws JsonProcessingException
     */
    public List<ReservationBean> getOutdatedActiveReservations() throws JsonProcessingException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, -applicationConfiguration.getActiveReservationTime()); // remove 48 hours

        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateString = simpleDateFormat.format(cal.getTime());
        String url = applicationConfiguration.getApiReservationBaseUrl() + "search/activeDateBefore?dateActive="+dateString;
        return callGet(url);

    }

    /**
     * Méthode qui ramène la reservation la plus ancienne
     * @param bookId
     * @return une réservation
     */
    public ReservationBean getFirstReservation(Integer bookId){
        String url = applicationConfiguration.getApiReservationBaseUrl() + "search/nextReservation?bookId=" + bookId;
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        ResponseEntity<ReservationBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, ReservationBean.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }
        return null;
    }

    public void deleteReservation(int reservationId){
        log.info("Suppression de la réservation: " + reservationId);
        String url = applicationConfiguration.getApiReservationBaseUrl() + reservationId;
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
    }

    /**
     * Cette méthode effectue un appel à l'API reservation
     * @param url
     * @return une liste de ReservationBean
     * @throws JsonProcessingException
     */
    private List<ReservationBean> callGet(String url) throws JsonProcessingException {
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        HttpEntity<String> request = new HttpEntity<>("body", securityService.authenticate());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        String data = response.getBody();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsNode = om.readTree(data);
        String content = jsNode.at("/_embedded/reservationEntities").toString();
        return om.readValue(content, new TypeReference<List<ReservationBean>>() {
        });
    }

    public void update(ReservationBean theReservation) {
        String url = applicationConfiguration.getApiReservationBaseUrl() + theReservation.getId();
        RestTemplate restTemplate = myRequestFactory.getRestTemplate();
        HttpEntity<ReservationBean> request = new HttpEntity<>(theReservation, securityService.authenticate());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

    }


}
