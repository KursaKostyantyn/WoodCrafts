package io.teamchallenge.woodCrafts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "payment_and_delivery")
public class PaymentAndDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "payment_type", nullable = false)
    @Builder.Default
    private String paymentType = "";
    @Column(name = "delivery", nullable = false)
    @Builder.Default
    private String delivery = "";
    @Column(name = "address", nullable = false)
    @Builder.Default
    private String address = "";
    @Column(name = "city", nullable = false)
    @Builder.Default
    private String city = "";
    @Column(name = "deliveryFee")
    @Builder.Default
    private String deliveryFee = "";
    @OneToOne(mappedBy = "paymentAndDelivery")
    private Order order;
}
