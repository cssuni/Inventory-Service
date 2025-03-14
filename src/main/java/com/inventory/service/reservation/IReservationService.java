package com.inventory.service.reservation;

import com.inventory.model.Reservation;

import java.util.List;

public interface IReservationService {

    public void createReservationForOrder(Long orderId);
    public void createReservation(Long orderId, Long itemId, Long Qty);
    public Reservation getReservation(Long reservationId);

    List<Reservation> getReservationByOrder(Long orderId);

    public List<Reservation> getProductReservations(Long productId);

}
