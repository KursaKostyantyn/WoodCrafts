package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springfox.documentation.annotations.ApiIgnore;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
