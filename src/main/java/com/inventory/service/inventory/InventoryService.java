package com.inventory.service.inventory;

import com.inventory.enums.TransactionType;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.InventoryTransaction;
import com.inventory.model.ItemInventory;
import com.inventory.repository.InventoryRepository;
import com.inventory.service.transaction.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService implements IinventoryService{

    private final InventoryRepository inventoryRepository;
    private final ITransactionService transactionService;

    @Override
    public List<ItemInventory> getAll(){
        return inventoryRepository.findAll();
    }

    @Override
    public String increaseInventory(Long productId, Long qty) {

        ItemInventory itemInventory = inventoryRepository.findById(productId)
                                        .orElseThrow(()-> new ResourceNotFoundException("Product Was Not Found"));

        if(qty > itemInventory.getBackOrderQty()) {
            itemInventory.setOnHand(itemInventory.getOnHand()+qty+itemInventory.getBackOrderQty());
            itemInventory.setBackOrderQty(0L);
        } else{
            itemInventory.setBackOrderQty(itemInventory.getBackOrderQty()-qty);
        }
        itemInventory.setTotalQuantity(itemInventory.getTotalQuantity()+qty);

        InventoryTransaction inventoryTransaction = transactionService.createTransaction(qty, TransactionType.INCREASE);
        inventoryTransaction.setItemInventory(itemInventory);

        List<InventoryTransaction> transactionList =
                Optional.ofNullable(itemInventory.getProductTransactions())
                        .orElseGet(ArrayList::new);

        transactionList.add(inventoryTransaction);
        itemInventory.setProductTransactions(transactionList);
        itemInventory.setLastUpdate(LocalDateTime.now());


        return "OnHand Inventory Increased to "+inventoryRepository.save(itemInventory).getOnHand();
    }

    @Override
    public ItemInventory decreaseInventory(Long productId, Long qty) {

        ItemInventory itemInventory = inventoryRepository.findById(productId)
                                        .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        if(qty > itemInventory.getOnHand()){
            itemInventory.setBackOrderQty(qty - itemInventory.getOnHand());
            itemInventory.setOnHand(0L);
        }

        itemInventory.setOnHand(itemInventory.getOnHand()-qty);
        InventoryTransaction inventoryTransaction = transactionService.createTransaction(-qty,TransactionType.ORDER);
        inventoryTransaction.setItemInventory(itemInventory);

        List<InventoryTransaction> transactionList = itemInventory.getProductTransactions();
        transactionList.add(inventoryTransaction);
        itemInventory.setProductTransactions(transactionList);
        itemInventory.setLastUpdate(LocalDateTime.now());

        return inventoryRepository.save(itemInventory);


    }

    @Override
    public ItemInventory getInventory(Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product Was Not Found"));
    }


    @Override
    public ItemInventory updateInventory(Long productId, Long totalQty) {
        ItemInventory itemInventory = inventoryRepository.findById(productId)
                .orElse(new ItemInventory());

        itemInventory.setTotalQuantity(totalQty);
        itemInventory.setOnHand(totalQty);
        itemInventory.setProductId(productId);

        InventoryTransaction inventoryTransaction =
                transactionService.createTransaction(totalQty, TransactionType.UPDATE);

        ItemInventory savedItemInventory = inventoryRepository.save(itemInventory);

        inventoryTransaction.setItemInventory(savedItemInventory);

        List<InventoryTransaction> transactionList =
                Optional.ofNullable(itemInventory.getProductTransactions())
                        .orElseGet(ArrayList::new);


        transactionList.add(inventoryTransaction);
        savedItemInventory.setBackOrderQty(0L);
        savedItemInventory.setPreOrderQty(0L);
        savedItemInventory.setProductTransactions(transactionList);
        savedItemInventory.setLastUpdate(LocalDateTime.now());

        return inventoryRepository.save(savedItemInventory);
    }

    @Override
    public String deleteProduct(Long productId){

        inventoryRepository.deleteById(productId);

        return productId+" Deleted Successfully";
    }



}
