package com.library.emprunts.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class EmpruntEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    private String exemplaireBarcode;
    private Date dateEmprunt;
    private Date dateRetour;
    private Boolean isExtended;
}
