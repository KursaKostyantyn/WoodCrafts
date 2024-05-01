package io.teamchallenge.woodCrafts.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime update_date;
    private String address;
    @NotBlank(message = "Status cannot be blank")
    private String status;
    private List<ProductLineDto> productLines;
    private Boolean deleted;
    @NotBlank(message = "Total price cannot be blank")
    private Double totalPrice;
    private UserDto user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime updatedAt;
    private boolean paidStatus;
    private BigDecimal totalPayment;
    private String comment;
//    private PaymentAndDeliveryDto paymentAndDelivery;
}
