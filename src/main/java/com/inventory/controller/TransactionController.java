package com.inventory.controller;

import com.inventory.enums.TransactionType;
import com.inventory.model.InventoryTransaction;
import com.inventory.service.transaction.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/transaction")
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping("getById")
    public ResponseEntity<InventoryTransaction> getById(@RequestParam Long id){

        try{
            return ResponseEntity.ok().body(transactionService.getTransaction(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("getByType")
    public ResponseEntity<List<InventoryTransaction>> getByType(@RequestParam TransactionType transactionType){
        try{
            return ResponseEntity.ok().body(transactionService.getTransactionsByType(transactionType));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("getByDate")
    public ResponseEntity<List<InventoryTransaction>> getByDate(@RequestParam LocalDate date) {
        try {
            return ResponseEntity.ok().body(transactionService.getTransactionsOnDate(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
