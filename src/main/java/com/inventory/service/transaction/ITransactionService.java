package com.inventory.service.transaction;

import com.inventory.enums.TransactionType;
import com.inventory.model.InventoryTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionService {

    public InventoryTransaction createTransaction(Long deltaQty, TransactionType type);
    public InventoryTransaction getTransaction(Long id);
    public List<InventoryTransaction> getTransactionsByType(TransactionType transactionType);
//    public List<InventoryTransaction> getProductTransactions(Long productId);
    public List<InventoryTransaction> getTransactionsOnDate(LocalDate date);

}
