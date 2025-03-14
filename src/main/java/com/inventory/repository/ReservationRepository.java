package com.inventory.repository;

import com.inventory.model.ItemInventory;
import com.inventory.model.Reservation;
import com.inventory.service.inventory.IinventoryService;
import com.inventory.service.inventory.InventoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByOrderId(Long order);

    List<Reservation> findByItemInventory(ItemInventory itemInventory);
}
