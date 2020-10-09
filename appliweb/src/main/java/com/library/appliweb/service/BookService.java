package com.library.appliweb.service;

import com.library.appliweb.beans.BookBean;
import com.library.appliweb.beans.SearchBean;
import com.library.appliweb.proxies.BooksProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BooksProxy booksProxy;

        public List<BookBean> searchBook(SearchBean searchBean){

            int page = 0;
            int size = 20;
            PagedResources<BookBean> pages = booksProxy.rechercherUnLivre(page, size, searchBean.getAuthor(), searchBean.getTitle());

            return null;
        }
}
