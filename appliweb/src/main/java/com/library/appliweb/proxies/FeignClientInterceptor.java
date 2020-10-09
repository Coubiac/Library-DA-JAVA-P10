package com.library.appliweb.proxies;

import com.library.appliweb.service.SecurityService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe qui permet d'injecter le token bearer dans chaque requête feign
 */
@Component
public class FeignClientInterceptor  implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Autowired
    SecurityService securityService;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        if(securityService.isAuthenticated()){
            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, securityService.getAuthToken()));
        }

    }
}
