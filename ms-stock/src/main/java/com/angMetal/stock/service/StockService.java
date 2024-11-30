package com.angMetal.stock.service;

import com.angMetal.stock.entity.Product;
import com.angMetal.stock.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import models.FactureEvent;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final ProductRepository productRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    public StockService(ProductRepository productRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.productRepository = productRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    /**
     * Process the stock update based on the incoming FactureEvent
     * @param factureEvent The facture event with stock update details
     */
    public void processStockUpdate(FactureEvent factureEvent) {
        // Log the incoming stock update event
        logStockUpdate(factureEvent);

        // Find the product in the repository
        Product product = findProduct(factureEvent);
        if (product == null) {
            logger.error("Product not found with ID: {}", factureEvent.getProductId());
            return;
        }

        // Update stock quantity based on event type (VENTE or ACHAT)
        updateStockQuantity(product, factureEvent);

        // Save the updated product to Elasticsearch
        saveStockInElasticsearch(product);

        // Save the updated product to the database (make sure to update both Elasticsearch and database)
        try {
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("Error saving product to database", e);
        }
    }

    /**
     * Find product by ID from the repository
     * @param factureEvent The facture event containing the product ID
     * @return The product or null if not found
     */
    private Product findProduct(FactureEvent factureEvent) {
        return productRepository.findById(factureEvent.getProductId()).orElse(null);
    }

    /**
     * Update the product stock quantity
     * @param product The product whose stock will be updated
     * @param factureEvent The event containing the stock change details
     */
    private void updateStockQuantity(Product product, FactureEvent factureEvent) {
        if ("VENTE".equals(factureEvent.getType())) {
            // Decrease stock for sale events
            product.setQuantiteEnStock(product.getQuantiteEnStock() - factureEvent.getProductCount());
        } else if ("ACHAT".equals(factureEvent.getType())) {
            // Increase stock for purchase events
            product.setQuantiteEnStock(product.getQuantiteEnStock() + factureEvent.getProductCount());
        }
    }

    /**
     * Save the product stock information to Elasticsearch
     * @param product The product to be saved in Elasticsearch
     */
    private void saveStockInElasticsearch(Product product) {
        try {
            elasticsearchRestTemplate.save(product);
        } catch (Exception e) {
            logger.error("Error saving product to Elasticsearch", e);
        }
    }

    /**
     * Log the stock update event details for monitoring with Logstash
     * @param factureEvent The event containing stock change details
     */
    private void logStockUpdate(FactureEvent factureEvent) {
        logger.info("Stock update received: Type: {}, Product ID: {}, Quantity: {}",
                factureEvent.getType(), factureEvent.getProductId(), factureEvent.getProductCount());
    }
}
