package com.inventory.model;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryForCart {

        private Long productId;
        private Long totalQuantity;
        private Long onHand;
        private Long preOrderQty;
        private Long backOrderQty;
        private LocalDateTime lastUpdate;
}
