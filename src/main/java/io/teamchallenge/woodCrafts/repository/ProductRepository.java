package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByDeleted(boolean deleted, PageRequest pageRequest);

    Page<Product> findAllByNameContainingIgnoreCaseAndDeleted(PageRequest pageRequest, String name, boolean deleted);
}
