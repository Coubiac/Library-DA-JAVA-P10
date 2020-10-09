package com.library.books.config;

import com.library.books.entity.Book;
import com.library.books.entity.Exemplaire;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import org.springframework.stereotype.Component;

@Configuration
public class RepositoryConfigurer implements RepositoryRestConfigurer {

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Exemplaire.class);
    }
}
