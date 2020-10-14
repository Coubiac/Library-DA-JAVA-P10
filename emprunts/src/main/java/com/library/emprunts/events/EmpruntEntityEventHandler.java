package com.library.emprunts.events;

import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.services.EmpruntManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.logging.Logger;

@RepositoryEventHandler
public class EmpruntEntityEventHandler {
    Logger logger = Logger.getLogger("Class AuthorEventHandler");

    @Autowired
    EmpruntManager empruntManager;

    @HandleBeforeCreate
    public void handleEmpruntEntityBeforeCreate(EmpruntEntity empruntEntity){
        logger.info("Inside Emrpunt BeforeCreate");
    }

    @HandleAfterCreate
    public void handleEmpruntEntityAfterCreate(EmpruntEntity empruntEntity){
        empruntManager.updateReservationList(empruntEntity);

    }



}
