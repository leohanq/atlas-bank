package com.atlas.bank.transaction.service.listener;

import com.atlas.bank.domain.event.TransactionExecutedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @EventListener
    public void onTransactionExecuted(TransactionExecutedEvent event) {
        log.info("Notification: Transaction executed with ID: {}, Type: {}, Source Account ID: {}, Target Account ID: {}, Amount: {}, Fee: {}",
                event.transactionId(), event.type(), event.sourceAccountId(), event.targetAccountId(), event.amount(), event.fee());
    }

}
