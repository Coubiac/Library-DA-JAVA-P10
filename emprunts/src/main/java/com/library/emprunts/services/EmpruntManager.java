package com.library.emprunts.services;

import com.library.emprunts.beans.BookBean;
import com.library.emprunts.beans.ExemplaireBean;
import com.library.emprunts.beans.ReservationBean;
import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.exceptions.EmpruntManagerException;
import com.library.emprunts.proxies.ExemplaireProxy;
import com.library.emprunts.proxies.ReservationsProxy;
import com.library.emprunts.repository.EmpruntEntityRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EmpruntManager {
    Logger logger = Logger.getLogger("Class AuthorEventHandler");

    @Autowired
    ExemplaireProxy exemplaireProxy;

    @Autowired
    ReservationsProxy reservationsProxy;

    @Autowired
    EmpruntEntityRepository repository;


    public void updateReservationListOnCreate(EmpruntEntity empruntEntity) {
        ReservationBean theReservarion = getTheReservation(empruntEntity);
        if (theReservarion != null) {
            deleteReservation(theReservarion);
        }
    }

    public void updateReservationListOnDelete(String barcode){
        ExemplaireBean theExemplaire = exemplaireProxy.recupererExemplaire(barcode).getContent();
        BookBean theBook = theExemplaire.getBook();
        try {
            ReservationBean theReservation = reservationsProxy.findNextReservation(theBook.getId()).getContent();
            theReservation.setActive(true);
            theReservation.setDateActive(new Date());
            reservationsProxy.update(theReservation.getId(),theReservation);
        } catch (Exception e) {

        }


    }

    private ReservationBean getTheReservation(EmpruntEntity empruntEntity) {
        String barcode = empruntEntity.getExemplaireBarcode();
        ExemplaireBean theExemplaire = exemplaireProxy.recupererExemplaire(barcode).getContent();
        BookBean theBook = theExemplaire.getBook();
        Collection<ReservationBean> reservations =
                reservationsProxy.findReservationByBookIdAndUserId(empruntEntity.getUserId(), theBook.getId())
                        .getContent();
        if (reservations.isEmpty()){
            logger.info("Il n'y a pas de reservation active");
            return null;
        }
        else {
            logger.info("une reservation a été trouvée");
            return reservations.iterator().next();

        }

    }

    private void deleteReservation(ReservationBean reservationBean) {
        reservationsProxy.deleteReservation(reservationBean.getId());
    }

    public boolean estDejaEmprunte(EmpruntEntity empruntEntity){
        List<EmpruntEntity> empruntEntities = repository
                .findEmpruntEntitiesByUserIdAndExemplaireBarcode(empruntEntity.getUserId(), empruntEntity.getExemplaireBarcode());
        return !empruntEntities.isEmpty();
    }

}
