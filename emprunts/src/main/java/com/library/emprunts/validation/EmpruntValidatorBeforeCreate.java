package com.library.emprunts.validation;

import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.exceptions.InvalidEmpruntException;
import com.library.emprunts.services.EmpruntManager;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;


@Component("beforeCreateValidator")
public class EmpruntValidatorBeforeCreate implements Validator{

    @Autowired
    EmpruntManager empruntManager;


    @Override
    public boolean supports(Class<?> clazz) {
        return EmpruntEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EmpruntEntity empruntEntity = (EmpruntEntity) target;

        /**
         * On ne peut pas réserver un exemplaire déja reservé.
         */
        if(empruntManager.estDejaEmprunte(empruntEntity)){
            throw new InvalidEmpruntException("Vous avez deja emprunté ce livre");
        }
    }
}
