package com.library.reservations.validation;

import com.library.reservations.entity.ReservationEntity;
import com.library.reservations.exceptions.ReservationServiceException;
import com.library.reservations.services.EmpruntService;
import com.library.reservations.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateReservationValidator")
public class ReservationValidator  implements Validator {

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private ReservationService reservationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ReservationEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ReservationEntity reservationEntity = (ReservationEntity) o;
        int bookId = ((ReservationEntity) o).getBookId();

        //Il n'est pas nécéssaire de réserver un exemplaire déja disponible
        if(empruntService.findExemplairesDispo(bookId) > 0){
            throw new ReservationServiceException("Des exemplaires sont deja disponibles");
        }

        //La liste de réservation ne peut comporter qu’un maximum de personnes correspondant à
        // 2x le nombre d’exemplaires de l’ouvrage.
        if((reservationService.getReservationQuantityByBookId(bookId)) >= (2 * empruntService.findExemplaires(bookId))){
            throw  new ReservationServiceException("Il y a trop de reservations pour ce livre, essayez plus tard");
        }

        //Il n’est pas possible pour un usager de réserver un ouvrage qu’il a déjà en cours d’emprunt
        if(empruntService.estDejaEmprunte(((ReservationEntity) o).getUserId(),((ReservationEntity) o).getBookId())){
            throw  new ReservationServiceException("vous avez deja emprunte ce livre");
        }


    }
}
