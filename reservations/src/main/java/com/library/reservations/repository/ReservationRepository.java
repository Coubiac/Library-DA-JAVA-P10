package com.library.reservations.repository;

import com.library.reservations.entity.ReservationEntity;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@RepositoryRestResource(path = "reservations")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByBookId(@Param("bookId") int bookId);
}
