package com.library.appliweb.controller;


import com.library.appliweb.beans.BookBean;
import com.library.appliweb.beans.EmpruntBean;
import com.library.appliweb.beans.ReservationBean;
import com.library.appliweb.configuration.ApplicationPropertiesConfiguration;
import com.library.appliweb.configuration.Credentials;
import com.library.appliweb.errors.CustomException;
import com.library.appliweb.proxies.BooksProxy;
import com.library.appliweb.requests.ReservationEntity;
import com.library.appliweb.service.EmpruntService;
import com.library.appliweb.service.ReservationService;
import com.library.appliweb.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    ApplicationPropertiesConfiguration appProperties;
    @Autowired
    SecurityService securityService;
    @Autowired
    private Credentials credentials;
    @Autowired
    private BooksProxy LivresProxy;
    @Autowired
    private EmpruntService empruntService;
    @Autowired
    private ReservationService reservationService;

    Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @GetMapping("/reservations")
    public String voirMesReservations(Model model, @RequestParam(required = false) Exception exception){
        if (!securityService.isAuthenticated()) return "security/login";

        List<ReservationBean> reservationBeans = reservationService.getReservationsByUserId(credentials.getUserId());
        if(reservationBeans == null){
            model.addAttribute("reservations",
                    null);
            return "mesReservations";
        }
            for (ReservationBean reservationBean:reservationBeans) {
                BookBean livre = reservationBean.getBookBean();
                List<ReservationBean> otherReservations = reservationService.getBookReservations(livre);
                livre.setReservationBeans(otherReservations);

                livre.setQuantiteDispo(empruntService.findExemplairesDispo(livre.getId()));
                String position = reservationService.
                        getPositionInReservationList(securityService.getAuthenticatedUser(), livre);
                if (position != null){
                    livre.setReservationPosition(position);
                }
                EmpruntBean nextActiveEmprunt = empruntService.getNextActiveEmprunt(livre);
                if (nextActiveEmprunt != null){
                    livre.setNextReturn(empruntService.getNextActiveEmprunt(livre).getDateRetourPrevu());
                }
            }

        model.addAttribute("reservations",
                reservationBeans);
        return "mesReservations";
    }

    @GetMapping("/reserver")
    public String Reserver(RedirectAttributes redirectAttributes, @RequestParam int bookId) throws IOException {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }
        try {
            reservationService.addReservation(bookId, credentials.getUserId());
            return "redirect:/reservations";
        }
        catch (CustomException exception){
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/reservations/delete")
    public RedirectView Delete(@RequestParam int reservationId){
        ReservationEntity theReservation = reservationService.getReservationEntityById(reservationId);
        if (securityService.isAuthenticated() &&
                (securityService.getAuthenticatedUser().getUserId().equals(theReservation.getUserId()))){
            reservationService.deleteReservation(reservationId);
            logger.info("la reservation n°" + reservationId + " a été supprimée");

        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/reservations");
        return redirectView;


    }
}
