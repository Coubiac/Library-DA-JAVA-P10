package com.library.reservations.repository;

import com.library.reservations.entity.ReservationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "reservations")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity, Long> {
}
