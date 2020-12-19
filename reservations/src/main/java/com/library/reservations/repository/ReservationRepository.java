package com.library.reservations.repository;

import com.library.reservations.entity.QReservationEntity;
import com.library.reservations.entity.ReservationEntity;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(path = "reservations")
public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity, Long>, QuerydslPredicateExecutor<ReservationEntity>, QuerydslBinderCustomizer<QReservationEntity> {

    @Override
    default void customize(QuerydslBindings bindings, QReservationEntity ReservationEntity) {

        // Make case-insensitive 'like' filter for all string properties
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    List<ReservationEntity> findAllByBookId(@Param("bookId") int bookId);

    List<ReservationEntity> findAllByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") String userId);

    @RestResource(path = "byBookIdAndUserId", rel="find a reservation for a user")
    List<ReservationEntity> findFirstByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") String userId);


    @RestResource(path = "byNextUser", rel="find the next reservation list")
    @Query(nativeQuery = true, value = "SELECT DISTINCT ON (BOOK_ID) * FROM RESERVATION_ENTITY ORDER BY DATE_RESERVATION ASC")
    List<ReservationEntity> findNextReservation();

    @RestResource(path = "nextReservation", rel="find the last reservation of a book")
    ReservationEntity findFirstByBookIdOrderByDateReservationAsc(@Param("bookId") int bookId);

    @RestResource(path = "activeDateBefore")
    List<ReservationEntity> findAllByActiveTrueAndDateActiveBefore(@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @Param("dateActive")Date dateActive);


}
