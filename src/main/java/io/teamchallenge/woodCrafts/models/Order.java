package io.teamchallenge.woodCrafts.models;

import io.teamchallenge.woodCrafts.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @NotNull
    @Builder.Default
    private Status status = Status.NEW;

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<ProductLine> productLines = new ArrayList<>();

    @Column(name = "deleted")
    @NotNull
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "total_price")
    @PositiveOrZero
    @NotNull
    @Builder.Default
    private Double totalPrice = 0.0;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    @ToString.Exclude
    private User user;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    @Column(name = "paid_status")
    private boolean paidStatus;

    @NotNull
    @Column(name = "total_payment")
    @PositiveOrZero
    @Builder.Default
    private BigDecimal totalPayment = BigDecimal.ZERO;

    @NotNull
    @Column(name = "comment", length = 500)
    @Builder.Default
    private String comment = "";

    @OneToOne
    @JoinColumn(name = "payment_and_delivery_id", referencedColumnName = "id")
    @ToString.Exclude
    private PaymentAndDelivery paymentAndDelivery;
}
