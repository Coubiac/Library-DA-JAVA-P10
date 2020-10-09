package com.library.books.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "exemplaire")
public class Exemplaire {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    private Integer barcode;
    private Date dateAchat;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
