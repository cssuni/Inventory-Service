package com.inventory.controller;


import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.Reservation;
import com.inventory.service.reservation.IReservationService;
import com.inventory.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reservation")
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping("create")
    public void createReservation(@RequestParam Long orderId){

        try {
            reservationService.createReservationForOrder(orderId);
        }
        catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("get")
    public ResponseEntity<Reservation> getReservation(@RequestParam Long id){

        try{
            return ResponseEntity.ok().body(reservationService.getReservation(id));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getByOrder")
    public ResponseEntity<List<Reservation>> getByOrder(@RequestParam Long orderId){

        try{
            return ResponseEntity.ok(reservationService.getReservationByOrder(orderId));
        } catch (ResourceNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getByProduct")
    public ResponseEntity<List<Reservation>> getByProduct(@RequestParam Long productId){

        try{
            return ResponseEntity.ok(reservationService.getProductReservations(productId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
