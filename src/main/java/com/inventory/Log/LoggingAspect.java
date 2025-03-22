package com.inventory.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.inventory.controller.InventoryController.*(..)) ||" +
            "execution(* com.inventory.controller.ReservationController.*(..)) ||" +
            "execution(* com.inventory.controller.TransactionController.*(..)) ||" +
            "execution(* com.inventory.service.inventory.InventoryService.*(..)) ||" +
            "execution(* com.inventory.service.transaction.TransactionService.*(..)) ||" +
            "execution(* com.inventory.service.reservation.ReservationService.*(..)) ||"+
            "execution(* com.inventory.service.redis.RedisService.*(..)) ||"+
            "execution(* com.inventory.config.*.*(..)) "
          )
    public void logBeforeMethodCall(JoinPoint jp) {
        LOGGER.info("{} Method Called", jp.getSignature().getName());
    }

    @After("execution(* com.inventory.controller.InventoryController.*(..)) ||" +
            "execution(* com.inventory.controller.ReservationController.*(..)) ||" +
            "execution(* com.inventory.controller.TransactionController.*(..)) ||" +
            "execution(* com.inventory.service.inventory.InventoryService.*(..)) ||" +
            "execution(* com.inventory.service.transaction.TransactionService.*(..)) ||" +
            "execution(* com.inventory.service.reservation.ReservationService.*(..)) ||"+
            "execution(* com.inventory.service.redis.RedisService.*(..)) ||"+
            "execution(* com.inventory.config.JwtValidationFilter.*(..)) "
    )
    public void logMethodExecuted(JoinPoint jp) {
        LOGGER.info("{} Method Completed", jp.getSignature().getName());
    }
}