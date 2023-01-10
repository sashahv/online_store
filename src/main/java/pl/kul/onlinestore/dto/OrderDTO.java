package pl.kul.onlinestore.dto;

import lombok.*;
import pl.kul.onlinestore.entity.ShoppingCart;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class OrderDTO {
    private String orderDescription;
    private List<ShoppingCart> cartItems;
    private String userEmail;
}
