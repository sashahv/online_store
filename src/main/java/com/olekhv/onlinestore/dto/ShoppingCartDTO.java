package com.olekhv.onlinestore.dto;

import com.olekhv.onlinestore.entity.CartItem;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ShoppingCartDTO {
    private List<CartItem> cartItems;
}
