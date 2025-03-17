package com.inventory.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.model.InventoryTransaction;
import com.inventory.model.ItemInventory;
import com.inventory.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;


    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete("itemInventory:"+key);
    }


    public ItemInventory saveItemInventoryInRedis(ItemInventory itemInventory){

        try{
            String itemInventoryJson = objectMapper.writeValueAsString(itemInventory);
            redisTemplate.opsForValue().set("itemInventory:"+itemInventory.getProductId().toString(),itemInventoryJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return itemInventory;
    }

    public ItemInventory getItemInventoryFromRedis(Long id){
        String itemInventoryJson = (String)redisTemplate.opsForValue().get("itemInventory:"+id.toString());

        if(itemInventoryJson != null) {
            try {
                return objectMapper.readValue(itemInventoryJson, ItemInventory.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public boolean checkItemInventoryInRedis(Long productId){
        String itemInventoryJson = (String)redisTemplate.opsForValue().get("itemInventory:"+productId.toString());

        return itemInventoryJson != null;
    }


    public InventoryTransaction getTransactionFromRedis(Long id) {
        String transactionJson = (String) redisTemplate.opsForValue().get("transaction:"+id.toString());

        if (transactionJson != null) {
            try {
                return objectMapper.readValue(transactionJson, InventoryTransaction.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }
    public InventoryTransaction saveInventoryTransactionInRedis(InventoryTransaction transaction){

        try{
            String itemInventoryJson = objectMapper.writeValueAsString(transaction);
            redisTemplate.opsForValue().set("transaction:"+transaction.getTransactionId().toString(),itemInventoryJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

    public Reservation getReservationFromRedis(Long id) {
        String reservationJson = (String) redisTemplate.opsForValue().get("reservation:"+id.toString());

        if (reservationJson != null) {
            try {
                return objectMapper.readValue(reservationJson, Reservation.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }
    public Reservation saveReservationInRedis(Reservation reservation){

        try{
            String reservationJson = objectMapper.writeValueAsString(reservation);
            redisTemplate.opsForValue().set("reservation:"+reservation.getReservationId().toString(), reservationJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

}
