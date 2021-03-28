package com.library.emprunts.proxies;


import com.library.emprunts.beans.ExemplaireBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;


import org.springframework.hateoas.EntityModel;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "books")
@RibbonClient(name = "books")
public interface ExemplaireProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/exemplaires/search/byBarcode?barcode={barcode}&projection=withBook")
    EntityModel<ExemplaireBean> recupererExemplaire(@PathVariable("barcode") String barcode);
}
