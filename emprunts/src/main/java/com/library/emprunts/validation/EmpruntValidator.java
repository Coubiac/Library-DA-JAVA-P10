package com.library.emprunts.validation;

import com.library.emprunts.data.EmpruntEntity;
<<<<<<< HEAD
import com.library.emprunts.exceptions.InvalidEmpruntException;
import org.apache.commons.lang.time.DateUtils;
=======
import com.library.emprunts.exceptions.EmpruntManagerException;
import com.library.emprunts.repository.EmpruntEntityRepository;
import com.library.emprunts.services.EmpruntManager;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> feature/Systeme_de_reservation
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

<<<<<<< HEAD
import java.util.Date;

@Component("beforeSaveEmpruntEntityValidator")
public class EmpruntValidator implements Validator {
=======
@Component("beforeCreateEmpruntValidator")
public class EmpruntValidator implements Validator{

    @Autowired
    EmpruntEntityRepository repository;

    @Autowired
    EmpruntManager empruntManager;

>>>>>>> feature/Systeme_de_reservation
    @Override
    public boolean supports(Class<?> clazz) {
        return EmpruntEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
<<<<<<< HEAD
        EmpruntEntity empruntEntity = (EmpruntEntity) target;

        Date today = new Date();
        Date dateEmprunt = empruntEntity.getDateEmprunt();
        Date dateRetourPrevu = DateUtils.addMonths(dateEmprunt,2);

        //Il ne doit pas être possible pour l’usager de prolonger un prêt si la date de fin de prêt est dépassée.
        if (dateRetourPrevu.before(today)){
            throw new InvalidEmpruntException("Il est trop tard pour prolonger un emprunt");
        }


=======

        EmpruntEntity empruntEntity = (EmpruntEntity) target;

        /**
         * On ne peut pas réserver un exemplaire déja reservé.
         */
        if(empruntManager.estDejaEmprunte(empruntEntity)){
            throw new EmpruntManagerException("Vous avez déja emprunté ce livre");
        }

>>>>>>> feature/Systeme_de_reservation
    }
}
