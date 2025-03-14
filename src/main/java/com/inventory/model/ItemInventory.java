package com.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class ItemInventory {

    @Id
    private Long productId;
    private Long totalQuantity;
    private Long onHand;
    private Long preOrderQty;
    private Long backOrderQty;
    private LocalDateTime lastUpdate;


    @JsonIgnore
    @OneToMany(mappedBy = "itemInventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryTransaction> productTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "itemInventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;



}
