package com.library.emprunts.beans;

import lombok.Data;

import java.util.Date;

/**
 * Cette classe représente l'exemplaire d'un livre
 */
@Data
public class ExemplaireBean {
    private Integer id;
    private Integer barcode;
    private Date dateAchat;
    private BookBean book;
}
