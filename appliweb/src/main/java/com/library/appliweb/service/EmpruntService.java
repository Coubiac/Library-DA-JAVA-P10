package com.library.appliweb.service;

import com.library.appliweb.beans.EmpruntBean;
import com.library.appliweb.beans.ExemplaireBean;
import com.library.appliweb.configuration.ApplicationPropertiesConfiguration;
import com.library.appliweb.configuration.Credentials;
import com.library.appliweb.proxies.BooksProxy;
import com.library.appliweb.proxies.EmpruntsProxy;
import com.library.appliweb.proxies.ExemplaireProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntsProxy empruntsProxy;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    @Autowired
    private Credentials credentials;

    @Autowired
    private BooksProxy booksProxy;

    @Autowired
    private ExemplaireProxy exemplaireProxy;


    public Boolean isExemplaireDispo(int barcode) {
        Collection<EmpruntBean> emprunts = empruntsProxy.findByExemplaireBarcode(barcode).getContent();

        if (emprunts.isEmpty()) return true;
        Boolean returnValue = true;
        for (EmpruntBean emprunt : emprunts) {
            if (emprunt.getDateRetour() == null) {
                returnValue = false;
            }

        }
        return returnValue;
    }

    public Integer findExemplairesDispo(int bookId) {
        Collection<ExemplaireBean> exemplaireBeans = booksProxy.recupererExemplairesLivre(bookId).getContent();
        int exemplairesDispo = 0;
        for (ExemplaireBean exemplaireBean : exemplaireBeans) {

            if (isExemplaireDispo(exemplaireBean.getBarcode())) {

                exemplairesDispo++;
            }
        }

        return exemplairesDispo;
    }

    public PagedResources<EmpruntBean> getEmpruntsByUserId(String userId, int page, int size)
    {
        PagedResources<EmpruntBean> emprunts = empruntsProxy.findByUserId(userId, page,size);

        for (EmpruntBean emprunt:emprunts){
            ExemplaireBean theExemplaire = exemplaireProxy.recupererExemplaire(emprunt.getExemplaireBarcode()).getContent();
            emprunt.setBookId(theExemplaire.getBook().getId());
            emprunt.setBookTitle(theExemplaire.getBook().getTitle());

            Calendar c = Calendar.getInstance();
            c.setTime(emprunt.getDateEmprunt());
            if(emprunt.getIsExtended()){
                c.add(Calendar.DATE, appProperties.getDureeEmprunt() + appProperties.getDureeProlongation());
            }
            else {
                c.add(Calendar.DATE, appProperties.getDureeEmprunt());
            }
            emprunt.setDateRetourPrevu(c.getTime());
        }
        return emprunts;
    }

    public void prolonge(Integer empruntId) {
        String body = "{\"isExtended\": \"true\"}";
        String url = "http://localhost:9004/emprunts/emprunts/" + empruntId;
        this.sendPatchRequest(url,body);
    }


    private void sendPatchRequest(String url, String body){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(300);
        requestFactory.setReadTimeout(300);
        restTemplate.setRequestFactory(requestFactory);
        headers.add("Authorization", "Bearer " +  credentials.getToken());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.PATCH,
                request,
                String.class);

    }
}
