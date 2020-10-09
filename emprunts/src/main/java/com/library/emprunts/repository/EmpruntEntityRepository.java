package com.library.emprunts.repository;

import com.library.emprunts.data.EmpruntEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(path = "emprunts")
public interface EmpruntEntityRepository extends PagingAndSortingRepository<EmpruntEntity, Long> {
    Page<EmpruntEntity> findByExemplaireBarcode(String exemplaireBarcode, Pageable paging);
    Page<EmpruntEntity> findEmpruntEntitiesByUserId(String userId, Pageable paging);
    List<EmpruntEntity> findEmpruntEntitiesByDateEmpruntAfter(@DateTimeFormat(pattern = "yyyy-MM-dd")@Param("dateEmprunt")Date dateEmprunt);
    List<EmpruntEntity> findEmpruntEntitiesByDateEmpruntBeforeAndDateRetourIsNull(@DateTimeFormat(pattern = "yyyy-MM-dd")@Param("dateEmprunt")Date dateEmprunt);
}
