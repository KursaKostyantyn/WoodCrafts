package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Order;
import io.teamchallenge.woodCrafts.models.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
    List<ProductLine> findProductLinesByOrder(Order order);
}
