package com.library.appliweb.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ExemplaireBean {
    private Integer id;
    private Integer barcode;
    private Date dateAchat;
    private BookBean book;
}
