package com.inventory.model;

import com.inventory.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems ;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;


public void updateTotalAmountWithTax() {
    BigDecimal subtotal = orderItems.stream()
            .map(orderItem -> orderItem.getUnitPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.05));
    this.totalAmount = subtotal.add(tax);
    }


}
