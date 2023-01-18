package com.olekhv.onlinestore.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class CartItemDTO {
    private Long productId;
    private int quantity;
}
