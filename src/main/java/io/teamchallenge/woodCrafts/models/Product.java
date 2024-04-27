package io.teamchallenge.woodCrafts.models;

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

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    @NotNull
    @Builder.Default
    private Double price = 0.0;

    @Column(name = "name", nullable = false)
    @NotNull
    @Builder.Default
    private String name = "";

    @Column(name = "description", length = 1200)
    @NotNull
    @Builder.Default
    private String description = "";

    @ElementCollection
    @CollectionTable(name = "photos", joinColumns = @JoinColumn(name = "photo_id"))
    @Column(name = "photos")
    @Builder.Default
    private List<String> photos = new ArrayList<>();

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @Column(name = "weight")
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Double weight = 0.0;

    @Column(name = "height")
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Double height = 0.0;

    @Column(name = "length")
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Double length = 0.0;

    @Column(name = "width")
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Double width = 0.0;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Category category;

    @Column(name = "quantity")
    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Integer quantity = 0;

    @Column(name = "warranty")
    @NotNull
    @Builder.Default
    private Integer warranty = 0;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    private Material material;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;


    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @Builder.Default
    private List<ProductLine> productLines = new ArrayList<>();
}
