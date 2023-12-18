package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    List<Color> findAllByDeleted(boolean isDeleted);
}
