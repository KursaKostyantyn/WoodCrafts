package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findAllByDeleted(boolean isDeleted);
}
