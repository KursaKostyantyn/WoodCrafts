package io.teamchallenge.woodCrafts.utils;

import io.teamchallenge.woodCrafts.models.Category;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductSpecificationsUtils {

    public static Specification<Product> filterProduct
            (
                    List<Category> categories,
                    List<Color> colors,
                    List<Material> materials,
                    int minPrice,
                    int maxPrice
            ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categories != null && !categories.isEmpty()) {
                predicates.add(root.get("category").in(categories));
            }
            if (colors != null && !colors.isEmpty()) {
                predicates.add(root.get("color").in(colors));
            }
            if (materials!=null && !materials.isEmpty()){
                predicates.add(root.get("material").in(materials));
            }
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<Product> isAvailable(boolean isAvailable){
        return ((root, query, criteriaBuilder) -> {
           List<Predicate> predicates = new ArrayList<>();

           if(isAvailable){
               predicates.add(criteriaBuilder.greaterThan(root.get("quantity"),0));
           }
           return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
