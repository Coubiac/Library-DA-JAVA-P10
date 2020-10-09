package com.library.appliweb.proxies;

import com.library.appliweb.beans.BookBean;
import com.library.appliweb.beans.ExemplaireBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "zuul-server")
@RibbonClient(name = "books")
public interface BooksProxy {



    @GetMapping("/books/books/?page={page}&size={size}&projection=withExemplaires")
    PagedResources<BookBean> listeDesLivres(@PathVariable("page") int page, @PathVariable("size") int size);

    @GetMapping("/books/books?page={page}&size={size}&author={author}&title={title}&projection=withExemplaires")
    PagedResources<BookBean> rechercherUnLivre(@PathVariable("page") int page, @PathVariable("size") int size,
                                               @PathVariable("author") String author, @PathVariable("title") String title);


    @GetMapping(value = "/books/books/{id}?projection=withExemplaires")
    Resource<BookBean> recupererUnLivre(@PathVariable("id") int id);

    @GetMapping(value = "/books/books/{bookId}/exemplaires")
    PagedResources<ExemplaireBean> recupererExemplairesLivre(@PathVariable("bookId") int bookId);


}
