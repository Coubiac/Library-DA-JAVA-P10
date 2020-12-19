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

import java.util.ArrayList;
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
            updateBookReservationList(reservation.getBookId());
            sendNotificationExemplaireDispo(reservation);
        }

    }


    /**
     * Cette tache planifiée va rechercher toutes les reservations ayant la propriété "active" sur True et va supprimer
     * celles dont la propriété dateActive est plus ancienne que 48h.
     * Pour chaque liste de réservation, la reservation dont la date est la plus éloignée est ensuite marquée comme
     * active.
     * @see ReservationBean
     */
    @Scheduled(cron = "10 * * * * ?")
    public void supprimeLesReservationsDontLeDelaiEstDepasse() throws JsonProcessingException {
        List<ReservationBean> reservationBeansToBeDeleted = reservationService.getOutdatedActiveReservations();

        for (ReservationBean theReservationToBeDeleted:reservationBeansToBeDeleted
             ) {
            int bookId = theReservationToBeDeleted.getBookId();
            reservationService.deleteReservation(theReservationToBeDeleted.getId().intValue());
            log.info("une reservation a été supprimée: " + theReservationToBeDeleted.getId());

            //Ensuite on met à jour la liste des reservations
            updateBookReservationList(bookId);

        }

    }


    /**
     *  Méthode qui met à jour la liste de reservation d'un livre. Le but est d'activer la reservation.
     * @param bookId
     * @throws JsonProcessingException quand il y a un problème de deserialization.
     */
    private void updateBookReservationList(int bookId) throws JsonProcessingException {
        ReservationBean nextReservation = reservationService.getFirstReservation(bookId);
        if(nextReservation != null){
            UserBean theUser = userService.getUserById(nextReservation.getUserId());
            if (nextReservation.getDateActive() == null){
                nextReservation.setDateActive(new Date());
                nextReservation.setActive(true);
                reservationService.update(nextReservation);
                log.info("La reservation de " + theUser.getFirstName() + " " + theUser.getLastName() + " est active");
            }

        }
    }

    /**
     * Cette méthode récupère la liste des exemplaires disponibles à partir d'une réservation. Si un exemplaire est dispo
     * Un mail est envoyé à la personne suivante sur la liste
     * @param reservation
     * @throws JsonProcessingException quand il y a un problème de deserialization.
     */
    private void sendNotificationExemplaireDispo(ReservationBean reservation) throws JsonProcessingException {

        List<ExemplaireBean> exemplaireBeans = exemplaireService.getExemplaireByBookId(reservation.getBookId());
        for (ExemplaireBean exemplaire:exemplaireBeans
        ) {
            if(exemplaireService.isExemplaireDispo(exemplaire.getBarcode())){
                UserBean theUser = userService.getUserById(reservation.getUserId());
                String theMessage = "Vous avez reservé un exemplaire de " +
                        exemplaireService.getBookById(reservation.getBookId() ).getTitle() +
                        ". Un exemplaire est à nouveau disponible, vous avez 48h pour venir le chercher";
                mailService.sendmail(theUser, "Votre Reservation", theMessage );
                log.info("Exemplaire dispo: " + theUser.getEmail() + " => " +exemplaireService.getBookById(reservation.getBookId() ).getTitle() );

            }
        }
    }
}