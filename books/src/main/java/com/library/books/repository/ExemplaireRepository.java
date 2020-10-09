package com.library.books.repository;

import com.library.books.entity.Exemplaire;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "exemplaires")
public interface ExemplaireRepository extends PagingAndSortingRepository<Exemplaire, Integer> {
    @RestResource(path = "byBarcode", rel="find by barcode")
    Exemplaire findOneByBarcode(@Param("barcode") int barcode);
    
}
