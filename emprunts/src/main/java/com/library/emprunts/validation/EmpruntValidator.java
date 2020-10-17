package com.library.emprunts.validation;

import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.exceptions.InvalidEmpruntException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component("beforeSaveEmpruntEntityValidator")
public class EmpruntValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return EmpruntEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmpruntEntity empruntEntity = (EmpruntEntity) target;

        Date today = new Date();
        Date dateEmprunt = empruntEntity.getDateEmprunt();
        Date dateRetourPrevu = DateUtils.addMonths(dateEmprunt,2);

        //Il ne doit pas être possible pour l’usager de prolonger un prêt si la date de fin de prêt est dépassée.
        if (dateRetourPrevu.before(today)){
            throw new InvalidEmpruntException("Il est trop tard pour prolonger un emprunt");
        }


    }
}
