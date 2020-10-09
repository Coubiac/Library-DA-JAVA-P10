package com.library.batch.beans;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class EmpruntBean {
    private Long id;
    private String userId;
    private String exemplaireBarcode;
    private Date dateEmprunt;
    private Date dateRetour;
    private Date dateRetourPrevu;
    private Boolean isExtended;
    private BookBean theBook;


    public String getDateEmpruntAsString(String pattern){
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(dateEmprunt);
    }

    public String getDateRetourPrevuAsString(String pattern){
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(dateRetourPrevu);
    }
}
