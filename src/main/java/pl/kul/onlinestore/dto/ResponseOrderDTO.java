package pl.kul.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ResponseOrderDTO {
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private int invoiceNumber;
    private Long orderId;
    private String orderDescription;
}
