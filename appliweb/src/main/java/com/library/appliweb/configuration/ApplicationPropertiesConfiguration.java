package com.library.appliweb.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("appliweb")
@Data
@RefreshScope
public class ApplicationPropertiesConfiguration {

    private int livresParPage;
    private int dureeEmprunt;
    private int dureeProlongation;
}
