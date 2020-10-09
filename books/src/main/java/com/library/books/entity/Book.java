package com.library.books.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@QueryEntity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Lob
    private String overview;
    @Lob
    private String synopsis;

    @OneToMany(mappedBy = "book", fetch=FetchType.EAGER)
    private List<Exemplaire> exemplaires = new ArrayList<>();
}
