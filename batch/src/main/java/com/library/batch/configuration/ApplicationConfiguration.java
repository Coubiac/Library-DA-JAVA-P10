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
    String smtpHost;
    String smtpPort;
    String apiUserBaseUrl;
    String apiEmpruntBaseUrl;
    String apiBookBaseUrl;
    String apiReservationBaseUrl;
    String apiUser;
    String apiPassword;
    int joursEmprunt;
    int joursProlongation;
    int activeReservationTime;

}
