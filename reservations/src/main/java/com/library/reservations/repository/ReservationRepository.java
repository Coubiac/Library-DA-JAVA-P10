package com.library.reservations.repository;

import com.library.reservations.entity.ReservationEntity;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@RepositoryRestResource(path = "reservations")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByBookId(@Param("bookId") int bookId);

    @RestResource(path = "byBookIdAndUserId", rel="find a reservation for a user")
    List<ReservationEntity> findFirstByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") String userId);


    @RestResource(path = "byNextUser", rel="find the next reservation list")
    @Query(nativeQuery = true, value = "SELECT DISTINCT ON (BOOK_ID) * FROM RESERVATION_ENTITY ORDER BY DATE_RESERVATION ASC")
    List<ReservationEntity> findNextReservation();

    @RestResource(path = "nextReservation", rel="find the last reservation ok a book")
    ReservationEntity findFirstByBookIdOrderByDateReservationAsc(@Param("bookId") int bookId);


}
