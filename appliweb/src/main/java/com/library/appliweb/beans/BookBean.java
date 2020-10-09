package com.library.appliweb.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookBean {
    private Integer id;
    private String title;
    private String author;
    private String titleSlug;
    private String authorSlug;
    private Long isbn13;
    private Long isbn10;
    private String format;
    private Date pubDate;
    private String subjects;
    private Integer pages;
    private String overview;
    private String synopsis;
    private Integer quantiteDispo;
    private List<ExemplaireBean> exemplaires = new ArrayList<>();

    @Override
    public String toString() {
        return "BookBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", titleSlug='" + titleSlug + '\'' +
                ", authorSlug='" + authorSlug + '\'' +
                ", isbn13=" + isbn13 +
                ", isbn10=" + isbn10 +
                ", format='" + format + '\'' +
                ", pubDate=" + pubDate +
                ", subjects='" + subjects + '\'' +
                ", pages=" + pages +
                ", overview='" + overview + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", exemplaires=" + exemplaires +
                '}';
    }
}
