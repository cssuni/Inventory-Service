package com.inventory.repository;

import com.inventory.enums.TransactionType;
import com.inventory.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    List<InventoryTransaction> findByTransactionType(TransactionType transactionType);

//    List<InventoryTransaction> findByproductId(Long productId);


    List<InventoryTransaction> findByDate(LocalDate date);
}
