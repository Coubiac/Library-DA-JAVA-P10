package com.library.batch.schedulingtasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.batch.beans.EmpruntBean;
import com.library.batch.beans.UserBean;
import com.library.batch.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    MailService mailService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Autowired
    EmpruntService empruntService;

    @Autowired
    ExemplaireService exemplaireService;


    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "10 * * * * ?")
    public void reportCurrentTime() throws JsonProcessingException {
        List<EmpruntBean> emprunts = empruntService.getEmprunts();
        for (EmpruntBean empruntBean:emprunts){
            UserBean userBean = userService.getUserById(empruntBean.getUserId());
            if (empruntBean.getDateRetourPrevu().before(new Date()) ){
                mailService.sendmail(userBean,empruntBean,exemplaireService.getExemplaireByBarcode(empruntBean.getExemplaireBarcode()));
                log.info("Message envoyé à " + userBean.getEmail() + " concernant l'exemplaire n°" + empruntBean.getExemplaireBarcode());
            }
        }

    }
}