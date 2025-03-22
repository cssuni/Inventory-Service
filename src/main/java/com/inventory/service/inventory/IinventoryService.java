package com.inventory.service.inventory;

import com.inventory.model.InventoryForCart;
import com.inventory.model.InventoryTransaction;
import com.inventory.model.ItemInventory;

import java.util.List;

public interface IinventoryService {

    List<ItemInventory> getAll();

    public String increaseInventory(Long productId, Long Qty);
    public ItemInventory decreaseInventory(Long productId, Long Qty);
    public ItemInventory getInventory(Long productId);

    InventoryForCart getInvForCart(Long productId);

    public ItemInventory updateInventory(Long productId, Long totalQty);


    String deleteProduct(Long productId);

}
