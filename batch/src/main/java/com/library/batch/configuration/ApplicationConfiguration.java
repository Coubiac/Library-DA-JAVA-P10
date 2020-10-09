package com.library.batch.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("batch")
@Data
public class ApplicationConfiguration {

    String senderEmail;
    String senderPassword;
    String apiUserBaseUrl;
    String apiEmpruntBaseUrl;
    String apiBookBaseUrl;
    String apiUser;
    String apiPassword;
    int joursEmprunt;
    int joursProlongation;

}
