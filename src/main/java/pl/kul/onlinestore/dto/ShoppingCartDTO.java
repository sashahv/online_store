package pl.kul.onlinestore.dto;

import lombok.*;
import pl.kul.onlinestore.entity.CartItem;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ShoppingCartDTO {
    private List<CartItem> cartItems;
}
