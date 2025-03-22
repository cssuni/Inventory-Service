package com.inventory.controller;


import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.InventoryForCart;
import com.inventory.model.ItemInventory;
import com.inventory.service.inventory.IinventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/inventory")
public class InventoryController {

    private final IinventoryService inventoryService;

    @GetMapping("all")
    public ResponseEntity<List<ItemInventory>> getAll(){
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("get")
    public ResponseEntity<ItemInventory> getInventory(@RequestParam Long productId){

        try{
            return ResponseEntity.ok(inventoryService.getInventory(productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("getForCart")
    public InventoryForCart getInvForCart(@RequestParam Long productId){

        try{
            return inventoryService.getInvForCart(productId);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("increase")
    public  ResponseEntity<String> increaseInventory(@RequestParam Long productId, @RequestParam Long qty){

        try{
            return ResponseEntity.ok(inventoryService.increaseInventory(productId, qty));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("decrease")
    public  ResponseEntity<ItemInventory> decreaseInventory(@RequestParam Long productId, @RequestParam Long qty){

        try{
            return ResponseEntity.ok(inventoryService.decreaseInventory(productId, qty));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("update")
    public  ResponseEntity<ItemInventory> updateInventory(@RequestParam Long productId, @RequestParam Long qty){

        try{
            return ResponseEntity.ok(inventoryService.updateInventory(productId, qty));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteInventory(@RequestParam Long productId){

        return ResponseEntity.ok().body(inventoryService.deleteProduct(productId));
    }

}
