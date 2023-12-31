package io.teamchallenge.woodCrafts.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    private double price;

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
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn (name = "color_id", referencedColumnName = "id")
    @NotNull
    private Color color;

    @Column(name = "weight")
    @NotNull
    private double weight;

    @Column(name = "height")
    @NotNull
    private double height;

    @Column(name = "length")
    @NotNull
    private double length;

    @Column(name = "wight")
    @NotNull
    private double wight;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Category category;

    @Column(name = "quantity")
    @NotNull
    private int quantity;

    @Column(name = "warranty")
    @NotNull
    private int warranty;

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
}
