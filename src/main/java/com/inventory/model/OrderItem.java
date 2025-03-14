package com.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "order_item", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private String productTitle;
    private Long productId;
    private BigDecimal totalPrice;
    private String productUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;



}
