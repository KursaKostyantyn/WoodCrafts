package io.teamchallenge.woodCrafts.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE})
    private Product product;

    @Column(name = "quantity")
    @PositiveOrZero
    @Builder.Default
    private int quantity = 0;

    @Column(name = "total_product_line_amount")
    private double totalProductLineAmount;

}
