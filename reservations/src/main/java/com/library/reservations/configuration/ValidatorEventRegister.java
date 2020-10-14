package com.library.reservations.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de contourner un bug existant dans spring data rest:
 * https://jira.spring.io/browse/DATAREST-524
 * Si faisons une requete http POST qui génère l'événement beforeCreate,
 * notre application n'appellera pas le validateur car l'événement ne sera pas découvert,
 * en raison de ce bug.
 *
 * Une solution simple à ce problème consiste à insérer tous les événements
 * dans la classe ValidatingRepositoryEventListener de Spring Data REST :
 */
@Configuration
public class ValidatorEventRegister implements InitializingBean {

    @Autowired
    ValidatingRepositoryEventListener validatingRepositoryEventListener;

    @Autowired
    private Map<String, Validator> validators;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> events = Arrays.asList("beforeCreate");
        for (Map.Entry<String, Validator> entry : validators.entrySet()) {
            events.stream()
                    .filter(p -> entry.getKey().startsWith(p))
                    .findFirst()
                    .ifPresent(
                            p -> validatingRepositoryEventListener
                                    .addValidator(p, entry.getValue()));
        }
    }
}