package com.library.reservations.proxies;


import com.library.reservations.beans.EmpruntBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "emprunts")
@RibbonClient(name = "emprunts")
public interface EmpruntsProxy {

    @GetMapping(value = "/emprunts/")
    EntityModel<EmpruntBean> getEmprunt(@PathVariable("id") int id);

    @RequestMapping(method = RequestMethod.GET, value = "/emprunts/search/findByExemplaireBarcode?exemplaireBarcode={exemplaireBarcode}")
    PagedModel<EmpruntBean> findByExemplaireBarcode(@PathVariable("exemplaireBarcode") int barcode);

    @RequestMapping(method = RequestMethod.GET, value = "/emprunts/search/findEmpruntEntitiesByUserId?userId={userId}")
    PagedModel<EmpruntBean> findByUserId(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/emprunts/search/findEmpruntEntitiesByUserIdAndBookId?userId={userId}&bookId={bookId}&page={page}&size={size}")
    List<EmpruntBean> findByUserIdAndBookId(@PathVariable("userId") String userId, @PathVariable("bookId") int bookId, @PathVariable("page") int page, @PathVariable("size") int size);

    @RequestMapping(method = RequestMethod.PATCH, value = "/emprunts/{empruntId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EmpruntBean updateEmprunt(@PathVariable("empruntId") int empruntId, String exemplaire);
}
