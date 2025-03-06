package com.parkinglot.strategies;

import com.parkinglot.models.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultPaymentStrategy implements PaymentStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DefaultPaymentStrategy.class);

    public void processPayment(Invoice invoice, double amount) {
        logger.info("Processing payment of {} via {}", amount, invoice.getPaymentType());
    }
}