package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Long> {
}
