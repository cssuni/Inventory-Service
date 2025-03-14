package com.inventory.model;

import com.inventory.enums.OrderStatus;
import com.inventory.enums.Status;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    private Long orderId;
    private Long orderQty;
    private Long reservedQty;
    private Long backOrderQty;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "productId")
    @ManyToOne
    private ItemInventory itemInventory;

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", orderId=" + orderId +
                ", orderQty=" + orderQty +
                ", status='" + status + '\'' +
                ", productId=" + itemInventory.getProductId() +
                '}';
    }

    public void updateReservedQtyAndStatusAndBackOrderQty(Long ReqQty, Long onHand){
        if(onHand >= ReqQty) {
            reservedQty = ReqQty;
            status = Status.RESERVED;
            backOrderQty = 0L;
        }
        else{
            reservedQty = onHand;
            backOrderQty = ReqQty - onHand;
            status = Status.BACKORDER;
        }
    }


}
