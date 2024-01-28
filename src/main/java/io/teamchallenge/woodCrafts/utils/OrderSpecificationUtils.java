package io.teamchallenge.woodCrafts.utils;

import io.teamchallenge.woodCrafts.constants.Status;
import io.teamchallenge.woodCrafts.models.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecificationUtils {

    public static Specification<Order> filterOrders(
            boolean isDeleted,
            LocalDateTime fromCreationDate,
            LocalDateTime toCreationDate,
            double minTotal,
            double maxTotal,
            Status status
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (fromCreationDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), fromCreationDate));
            }
            if (toCreationDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), toCreationDate));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (isDeleted){
                predicates.add(criteriaBuilder.equal(root.get("deleted"), true));
            }
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalPrice"), minTotal));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("totalPrice"), maxTotal));


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
