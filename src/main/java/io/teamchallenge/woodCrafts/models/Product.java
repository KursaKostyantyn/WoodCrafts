package io.teamchallenge.woodCrafts.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
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
    private Double price;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "description")
    @NotNull
    private String description;

    @ElementCollection
    @CollectionTable(name = "photos", joinColumns = @JoinColumn(name = "photo_id"))
    @Column(name = "photos")
    private List<String> photos;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    @NotNull
    private Color color;

    @Column(name = "weight")
    @NotNull
    private Double weight;

    @Column(name = "height")
    @NotNull
    private Double height;

    @Column(name = "length")
    @NotNull
    private Double length;

    @Column(name = "width")
    @NotNull
    private Double width;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Category category;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Column(name = "warranty")
    @NotNull
    private Integer warranty;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @NotNull
    private Material material;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;


    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<ProductLine> productLines = new ArrayList<>();
}
