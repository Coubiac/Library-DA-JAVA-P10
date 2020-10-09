package com.library.books.entity.projection;

import com.library.books.entity.Book;
import com.library.books.entity.Exemplaire;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(name = "withExemplaires", types= Book.class)
public interface BookProjection {
    Integer getId();
    String getTitle();
    String getAuthor();
    String getTitleSlug();
    String getAuthorSlug();
    Long getIsbn13();
    Long getIsbn10();
    String getFormat();
    Date getPubDate();
    String getSubjects();
    Integer getPages();
    String getOverview();
    String getSynopsis();
    List<Exemplaire> getExemplaires();

}
