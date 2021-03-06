package com.library.reservations.beans;

import lombok.Data;

import java.util.Date;

/**
 * Cette classe représente un emprunt
 */
@Data
public class EmpruntBean {
    private Long id;
    private String userId;
    private String exemplaireBarcode;
    private int bookId;
    private String bookTitle;
    private Date dateEmprunt;
    private Date dateRetour;
    private Date dateRetourPrevu;
    private Boolean isExtended;
}
