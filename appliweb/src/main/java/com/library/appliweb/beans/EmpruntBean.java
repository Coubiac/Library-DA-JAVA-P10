package com.library.appliweb.beans;

import lombok.Data;

import java.util.Date;

@Data
public class EmpruntBean implements Comparable<EmpruntBean> {
    private Long id;
    private String userId;
    private String exemplaireBarcode;
    private int bookId;
    private String bookTitle;
    private Date dateEmprunt;
    private Date dateRetour;
    private Date dateRetourPrevu;
    private Boolean isExtended;

    @Override
    public int compareTo(EmpruntBean o) {
        if(getDateRetourPrevu() == null || o.getDateRetourPrevu() == null){
            return 0;
        }
        return getDateRetourPrevu().compareTo(o.getDateRetourPrevu());
    }
}
