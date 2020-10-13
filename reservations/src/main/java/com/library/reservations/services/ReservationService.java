package com.library.reservations.services;

import com.library.reservations.entity.ReservationEntity;
import com.library.reservations.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public int getReservationQuantityByBookId(int bookId){
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByBookId(bookId);
        return reservationEntities.size();
    }

}
