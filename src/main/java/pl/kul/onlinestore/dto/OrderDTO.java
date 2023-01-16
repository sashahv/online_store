package pl.kul.onlinestore.dto;

import lombok.*;
import pl.kul.onlinestore.entity.order.DeliveryAddress;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class OrderDTO {
    private String orderDescription;
    private DeliveryAddress deliveryAddress;
    private Long shoppingCartId;
    private String userEmail;
}
