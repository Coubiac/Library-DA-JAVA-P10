package com.library.emprunts.proxies;

import com.library.emprunts.beans.ReservationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservations")
public interface ReservationsProxy {


    @GetMapping("/reservations/search/byBookIdAndUserId?userId={userId}&bookId={bookId}")
    PagedModel<ReservationBean> findReservationByBookIdAndUserId(@PathVariable("userId") String userId, @PathVariable("bookId") int bookId);

    @DeleteMapping("/reservations/{reservationId}")
    void deleteReservation(@PathVariable("reservationId") Long reservationId);
}
