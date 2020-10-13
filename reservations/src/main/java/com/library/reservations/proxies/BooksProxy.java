package com.library.reservations.proxies;

import com.library.reservations.beans.BookBean;
import com.library.reservations.beans.ExemplaireBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "books")
@RibbonClient(name = "books")
public interface BooksProxy {



    @GetMapping("/books/?page={page}&size={size}&projection=withExemplaires")
    PagedModel<BookBean> listeDesLivres(@PathVariable("page") int page, @PathVariable("size") int size);

    @GetMapping("/books?page={page}&size={size}&author={author}&title={title}&projection=withExemplaires")
    PagedModel<BookBean> rechercherUnLivre(@PathVariable("page") int page, @PathVariable("size") int size,
                                               @PathVariable("author") String author, @PathVariable("title") String title);


    @GetMapping(value = "/books/{id}?projection=withExemplaires")
    EntityModel<BookBean> recupererUnLivre(@PathVariable("id") int id);

    @GetMapping(value = "/books/{bookId}/exemplaires")
    PagedModel<ExemplaireBean> recupererExemplairesLivre(@PathVariable("bookId") int bookId);

    @RequestMapping(method = RequestMethod.GET, value = "/exemplaires/search/byBarcode?barcode={barcode}&projection=withBook")
    EntityModel<ExemplaireBean> recupererExemplaire(@PathVariable("barcode") String barcode);


}
