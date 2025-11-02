package com.tp.benchmarkspringdatarestapi.repository;

import com.tp.benchmarkspringdatarestapi.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(
        collectionResourceRel = "categories",
        path = "categories"
)
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @RestResource(exported = true)
    Page<Category> findAll(Pageable pageable);
}
