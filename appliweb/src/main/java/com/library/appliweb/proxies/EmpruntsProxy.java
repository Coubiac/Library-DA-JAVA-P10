package com.library.appliweb.proxies;


import com.library.appliweb.beans.EmpruntBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "emprunts")
public interface EmpruntsProxy {

    @GetMapping(value = "/emprunts/emprunts/")
    Resource<EmpruntBean> getEmprunt(@PathVariable("id") int id);

    @RequestMapping(method = RequestMethod.GET, value = "/emprunts/emprunts/search/findByExemplaireBarcode?exemplaireBarcode={exemplaireBarcode}")
    PagedResources<EmpruntBean> findByExemplaireBarcode(@PathVariable("exemplaireBarcode") int barcode);

    @RequestMapping(method = RequestMethod.GET, value = "/emprunts/emprunts/search/findEmpruntEntitiesByUserId?userId={userId}&page={page}&size={size}")
    PagedResources<EmpruntBean> findByUserId(@PathVariable("userId") String userId, @PathVariable("page") int page, @PathVariable("size") int size);

    @RequestMapping(method = RequestMethod.PATCH, value = "/emprunts/emprunts/{empruntId}", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EmpruntBean updateEmprunt(@PathVariable("empruntId") int empruntId, String exemplaire);
}
