package com.inventory.model;

import com.inventory.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long quantityDelta;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType ;
    private LocalDateTime adjustedAt;
    private LocalDate date;

    @JoinColumn(name = "productId")
    @ManyToOne
    private ItemInventory itemInventory;

    @Override
    public String toString() {
        return "InventoryTransaction{" +
                "transactionId=" + transactionId +
                ", quantityDelta=" + quantityDelta +
                ", transactionType='" + transactionType + '\'' +
                ", adjustedAt=" + adjustedAt +
                ", productId=" + itemInventory.getProductId() +
                '}';
    }
}
