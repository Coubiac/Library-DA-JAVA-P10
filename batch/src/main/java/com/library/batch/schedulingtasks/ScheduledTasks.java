package com.library.batch.schedulingtasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.batch.beans.EmpruntBean;
import com.library.batch.beans.ExemplaireBean;
import com.library.batch.beans.ReservationBean;
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

    @Autowired
    ReservationService reservationService;


    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    /**
     * Cette tache planifiée, envoi un mail aux utilisateurs qui n'ont pas rendu l'exemplaire qu'ils ont emprunté à temps.
     * On récupère la liste de tous les emprunts et on compare la date de retour prévu avec la date actuelle. Si la date
     * de retour prévu est dans le passé, on envoi un mail de relance
     * @throws JsonProcessingException
     */
    @Scheduled(cron = "10 * * * * ?")
    public void bookReturnReminder() throws JsonProcessingException {
        List<EmpruntBean> emprunts = empruntService.getEmprunts();
        for (EmpruntBean empruntBean:emprunts){
            if (empruntBean.getDateRetourPrevu().before(new Date()) ){
                ExemplaireBean theExemplaire = exemplaireService.getExemplaireByBarcode(empruntBean.getExemplaireBarcode());
                String messageContent = "Cher client,"
                        + "\n\n Vous avez emprunté un livre et maintenant il faut nous le rendre !!!\n\n "
                        + "Titre du livre: " + theExemplaire.getBook().getTitle() + "\n"
                        + "Date d'emprunt: " + empruntBean.getDateEmpruntAsString("dd/MM/yyyy") + "\n"
                        + "Date de retour prévu avant: " + empruntBean.getDateRetourPrevuAsString("dd/MM/yyyy");
                String subject = "Votre livre emprunté";
                UserBean userBean = userService.getUserById(empruntBean.getUserId());
                mailService.sendmail(userBean,subject,messageContent);
                log.info("Message envoyé à " + userBean.getEmail() + " concernant l'exemplaire n°" + empruntBean.getExemplaireBarcode());
            }
        }
    }

    /**
     * Cette tache planifiée envoie un mail au premier utilisteur dans la liste d'attente quand un exemplaire du livre
     * réservé se libère.
     * @throws JsonProcessingException
     */
    @Scheduled(cron = "10 * * * * ?")
    public void leLivreReserveEstDispo() throws JsonProcessingException {

        List<ReservationBean> reservationBeans = reservationService.getNextReservations();

        for (ReservationBean reservation:reservationBeans
             ) {
            List<ExemplaireBean> exemplaireBeans = exemplaireService.getExemplaireByBookId(reservation.getBookId());
            for (ExemplaireBean exemplaire:exemplaireBeans
                 ) {
                if(exemplaireService.isExemplaireDispo(exemplaire.getBarcode())){
                    UserBean theUser = userService.getUserById(reservation.getUserId());
                    String theMessage = "Vous avez reservé un exemplaire de " +
                            exemplaireService.getBookById(reservation.getBookId() ).getTitle() +
                            ". Un exemplaire est à nouveau disponible, vous avez 48h pour venir le chercher";
                    mailService.sendmail(theUser, "Votre Reservation", theMessage );
                }
            }
        }

    }
}