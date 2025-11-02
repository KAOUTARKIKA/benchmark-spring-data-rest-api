package com.tp.benchmarkspringdatarestapi.repository;

import com.tp.benchmarkspringdatarestapi.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
        collectionResourceRel = "items",
        path = "items"
)
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Override
    @RestResource(exported = true)
    Page<Item> findAll(Pageable pageable);
    @RestResource(path = "by-category", rel = "by-category")
    Page<Item> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    /**
     * Variante anti-N+1 avec JOIN FETCH pour éviter les requêtes multiples.
     *
     * Endpoint généré : GET /items/search/findByCategoryIdWithJoinFetch?categoryId=1&page=0&size=20
     */
    @RestResource(path = "by-category-optimized", rel = "by-category-optimized")
    @Query("SELECT i FROM Item i JOIN FETCH i.category c WHERE c.id = :categoryId ORDER BY i.id")
    Page<Item> findByCategoryIdWithJoinFetch(@Param("categoryId") Long categoryId, Pageable pageable);
}