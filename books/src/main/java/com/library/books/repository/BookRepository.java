package com.library.books.repository;

import com.library.books.entity.Book;
import com.library.books.entity.QBook;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Integer>, QuerydslPredicateExecutor<Book>, QuerydslBinderCustomizer<QBook> {

    @Override
    default void customize(QuerydslBindings bindings, QBook book) {

        // Make case-insensitive 'like' filter for all string properties
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
