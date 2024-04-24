package io.teamchallenge.woodCrafts.models;

import io.teamchallenge.woodCrafts.constants.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
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
    private LocalDateTime localDateTime;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    @ToString.Exclude
    private List<ProductLine> productLines = new ArrayList<>();

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "total_price")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;
    @NotNull
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @NotNull
    @Column(name = "paid_status")
    private boolean paidStatus;
    @NotNull
    @Column(name = "total_payment")
    @PositiveOrZero
    private BigDecimal totalPayment;
    @NotNull
    @Column(name = "comment", length = 500)
    private String comment;
    @OneToOne
    @JoinColumn(name = "payment_and_delivery_id", referencedColumnName = "id")
    private PaymentAndDelivery paymentAndDelivery;
}
