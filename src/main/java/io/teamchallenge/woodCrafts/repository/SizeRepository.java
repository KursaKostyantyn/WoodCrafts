package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size,Long> {
}
