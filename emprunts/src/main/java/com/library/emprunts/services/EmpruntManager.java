package com.library.emprunts.services;

import com.library.emprunts.beans.BookBean;
import com.library.emprunts.beans.ExemplaireBean;
import com.library.emprunts.beans.ReservationBean;
import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.proxies.ExemplaireProxy;
import com.library.emprunts.proxies.ReservationsProxy;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Logger;

@Service
public class EmpruntManager {
    Logger logger = Logger.getLogger("Class AuthorEventHandler");

    @Autowired
    ExemplaireProxy exemplaireProxy;

    @Autowired
    ReservationsProxy reservationsProxy;


    public void updateReservationList(EmpruntEntity empruntEntity) {
        ReservationBean theReservarion = getTheReservation(empruntEntity);
        if (theReservarion != null) {
            deleteReservation(theReservarion);
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

}
