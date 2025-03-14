package com.inventory.service.reservation;


import com.inventory.exception.ResourceNotFoundException;
import com.inventory.feign.OrderInterface;
import com.inventory.model.ItemInventory;
import com.inventory.model.Order;
import com.inventory.model.OrderItem;
import com.inventory.model.Reservation;
import com.inventory.repository.ReservationRepository;
import com.inventory.service.inventory.IinventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService{

    private final ReservationRepository reservationRepository;
    private final IinventoryService inventoryService;
    private final OrderInterface orderInterface;

    @Override
    public void createReservationForOrder(Long orderId) {

        Order order = orderInterface.getOrder(orderId).getBody();
        Reservation reservation = new Reservation();
        reservation.setOrderId(orderId);

        System.out.println(order);

        if(order == null) throw new ResourceNotFoundException("Order Not Found");

        List<OrderItem> orderItems = Objects.requireNonNull(order).getOrderItems();

        if(orderItems != null){
            orderItems.forEach(orderItem ->
                    createReservation(orderId,orderItem.getProductId(), (long) orderItem.getQuantity()));
        }

    }

    @Override
    public void createReservation(Long orderId, Long itemId, Long qty) {

        Reservation reservation = new Reservation();
        reservation.setOrderId(orderId);
        reservation.setOrderQty(qty);
        ItemInventory itemInventory = inventoryService.getInventory(itemId);
        reservation.updateReservedQtyAndStatusAndBackOrderQty(qty, itemInventory.getOnHand());
        reservation.setItemInventory(inventoryService.decreaseInventory(itemId, qty));

        reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found"));
    }

    @Override
    public List<Reservation> getReservationByOrder(Long orderId) {
        return reservationRepository.findByOrderId(orderId);
    }

    @Override
    public List<Reservation> getProductReservations(Long productId) {

        ItemInventory itemInventory = inventoryService.getInventory(productId);

        return reservationRepository.findByItemInventory(itemInventory);
    }
}
