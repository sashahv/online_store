package com.olekhv.onlinestore.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "deliveryAddress")
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
