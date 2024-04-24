package io.teamchallenge.woodCrafts.repository;

import io.teamchallenge.woodCrafts.models.PaymentAndDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAndDeliveryRepository extends JpaRepository<PaymentAndDelivery, Long> {
}
