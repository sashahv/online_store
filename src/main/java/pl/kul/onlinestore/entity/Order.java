package pl.kul.onlinestore.entity;

import lombok.*;
import pl.kul.onlinestore.entity.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
@ToString
@Table(name = "order_tbl")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ShoppingCart.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<ShoppingCart> cartItems;

    public Order(Long id, String orderDescription, User user, List<ShoppingCart> cartItems) {
        this.id = id;
        this.orderDescription = orderDescription;
        this.user = user;
        this.cartItems = cartItems;
    }

    public Order(String orderDescription, User user, List<ShoppingCart> cartItems) {
        this.orderDescription = orderDescription;
        this.user = user;
        this.cartItems = cartItems;
    }
}
