package io.teamchallenge.woodCrafts.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;
    @Min(value = 0,message = "Price cannot be less than 0")
    private Double price;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    private List<String> photos;
    @Min(value = 0, message = "ColorId  must be 1 or more")
    private Long colorId;
    @Min(value = 0,message = "Weight cannot be less than 0")
    private Double weight;
    @Min(value = 0,message = "Height cannot be less than 0")
    private Double height;
    @Min(value = 0,message = "Length cannot be less than 0")
    private Double length;
    @Min(value = 0,message = "Width cannot be less than 0")
    private Double width;
    @Min(1)
    private Long categoryId;
    @Min(value = 0,message = "Quantity cannot be less than 0")
    private Integer quantity;
    private Integer warranty;
    @Min(value = 1,message = "MaterialId must be 1 or more")
    private Long materialId;
    private Boolean deleted;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonIgnore
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonIgnore
    private LocalDateTime updateDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty("creationDate")
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty("updateDate")
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
}
