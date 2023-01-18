package com.olekhv.onlinestore.dto;

import com.olekhv.onlinestore.entity.order.DeliveryAddress;
import lombok.*;

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
