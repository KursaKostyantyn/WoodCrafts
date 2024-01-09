package io.teamchallenge.woodCrafts.models;

import io.teamchallenge.woodCrafts.constants.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    @ToString.Exclude
    private List<ProductLine> productLines = new ArrayList<>();

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "total_price")
    private double totalPrice;

}
