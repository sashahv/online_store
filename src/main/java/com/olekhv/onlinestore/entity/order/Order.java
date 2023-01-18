package com.olekhv.onlinestore.entity.order;

import com.olekhv.onlinestore.entity.ShoppingCart;
import jakarta.persistence.*;
import lombok.*;
import com.olekhv.onlinestore.entity.user.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@Table(name = "order_tbl")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Long deliveryAddressId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private ShoppingCart shoppingCart;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
