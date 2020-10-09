package com.library.books.entity.projection;


import com.library.books.entity.Book;
import com.library.books.entity.Exemplaire;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "withBook", types= Exemplaire.class)
public interface ExemplaireProjection {
    Integer getId();
    Integer getBarcode();
    Date getDateAchat();
    Book getBook();
}
