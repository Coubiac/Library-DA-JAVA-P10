package com.library.appliweb.proxies;


import com.library.appliweb.requests.ReservationEntity;
import com.library.appliweb.requests.ReservationPostRequest;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "zuul-server")
@RibbonClient(name = "reservations")
public interface ReservationProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/reservations/reservations?bookId={bookId}")
    PagedResources<ReservationEntity> findAllByBookId(@PathVariable("bookId") int bookId);

    @RequestMapping(method = RequestMethod.GET, value = "/reservations/reservations?userId={userId}")
    PagedResources<ReservationEntity> findAllByUserId(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.POST, value = "/reservations/reservations/", headers = "Content-Type: application/json")
    void create(ReservationPostRequest reservationPostRequest);

}
