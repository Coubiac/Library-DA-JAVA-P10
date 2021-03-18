package com.library.emprunts.proxies;

import com.library.emprunts.beans.ReservationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "reservations")
public interface ReservationsProxy {


    @GetMapping("/reservations/search/byBookIdAndUserId?userId={userId}&bookId={bookId}")
    PagedModel<ReservationBean> findReservationByBookIdAndUserId(@PathVariable("userId") String userId, @PathVariable("bookId") int bookId);

    @GetMapping("/reservations/search/nextReservation?bookId={bookId}")
    EntityModel<ReservationBean> findNextReservation(@PathVariable("bookId") int bookId);

    @PutMapping("/reservations/{id}")
    void update(@PathVariable("id") Long id, ReservationBean reservationBean);

    @DeleteMapping("/reservations/{reservationId}")
    void deleteReservation(@PathVariable("reservationId") Long reservationId);
}
