package com.inventory.repository;

import com.inventory.model.ItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<ItemInventory, Long > {
}
