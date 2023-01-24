package com.olekhv.onlinestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.olekhv.onlinestore.entity.order.Order;
import com.olekhv.onlinestore.service.ShoppingCartService;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private List<CartItem> cartItems;
    private BigDecimal amount;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
}
