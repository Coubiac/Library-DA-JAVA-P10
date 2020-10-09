package com.library.appliweb.proxies;

import com.library.appliweb.beans.ExemplaireBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "books")
public interface ExemplaireProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/books/exemplaires/search/byBarcode?barcode={barcode}&projection=withBook")
    Resource<ExemplaireBean> recupererExemplaire(@PathVariable("barcode") String barcode);
}
