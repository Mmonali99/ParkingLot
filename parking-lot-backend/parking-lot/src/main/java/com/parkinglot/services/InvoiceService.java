package com.parkinglot.services;

import com.parkinglot.models.Invoice;
import com.parkinglot.repositories.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        logger.info("Fetching all invoices");
        List<Invoice> invoices = invoiceRepository.findAll();
        logger.info("Fetched {} invoices", invoices.size());
        return invoices;
    }

    @Transactional(readOnly = true)
    public Invoice getInvoiceById(Long id) {
        logger.info("Fetching invoice with ID: {}", id);
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (!invoiceOptional.isPresent()) {
            logger.error("Invoice not found with ID: {}", id);
            throw new RuntimeException("Invoice not found");
        }
        Invoice invoice = invoiceOptional.get();
        logger.info("Invoice fetched successfully: {}", id);
        return invoice;
    }
}