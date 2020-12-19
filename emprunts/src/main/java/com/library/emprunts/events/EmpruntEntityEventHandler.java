package com.library.emprunts.events;

import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.services.EmpruntManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;

import java.util.logging.Logger;
@Data
@RepositoryEventHandler
public class EmpruntEntityEventHandler {
    Logger logger = Logger.getLogger("Class EmpruntEntityEventHandler");

    @Autowired
    EmpruntManager empruntManager;

    private String barcode;

    @HandleBeforeCreate
    public void handleEmpruntEntityBeforeCreate(EmpruntEntity empruntEntity){
        logger.info("Inside Emrpunt BeforeCreate");
    }

    @HandleAfterCreate
    public void handleEmpruntEntityAfterCreate(EmpruntEntity empruntEntity){
        empruntManager.updateReservationListOnCreate(empruntEntity);

    }

    @HandleBeforeDelete
    public void handleEmpruntEntityBeforeDelete(EmpruntEntity empruntEntity){
        System.out.println(empruntEntity.getExemplaireBarcode());
        this.barcode = empruntEntity.getExemplaireBarcode();
    }

    @HandleAfterDelete
    public void handleEmpruntEntityAfterDelete(EmpruntEntity empruntEntity){
        System.out.println(this.barcode);
        empruntManager.updateReservationListOnDelete(this.barcode);
    }



}
