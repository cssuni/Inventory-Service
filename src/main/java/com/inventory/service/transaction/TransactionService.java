package com.inventory.service.transaction;

import com.inventory.enums.TransactionType;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.InventoryTransaction;
import com.inventory.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;


    @Override
    public InventoryTransaction createTransaction(Long deltaQty, TransactionType type) {

        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setQuantityDelta(deltaQty);
        transaction.setTransactionType(type);
        transaction.setAdjustedAt(LocalDateTime.now());
        transaction.setDate(LocalDate.now());

        return transaction;
    }

    @Override
    public InventoryTransaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Transaction Not Found"));
    }

    @Override
    public List<InventoryTransaction> getTransactionsByType(TransactionType transactionType) {

        return transactionRepository.findByTransactionType(transactionType);
    }

//    @Override
//    public List<InventoryTransaction> getProductTransactions(Long productId) {
//        return transactionRepository.findByproductId(productId);
//    }

    @Override
    public List<InventoryTransaction> getTransactionsOnDate(LocalDate date) {
        return transactionRepository.findByDate(date);
    }
}
