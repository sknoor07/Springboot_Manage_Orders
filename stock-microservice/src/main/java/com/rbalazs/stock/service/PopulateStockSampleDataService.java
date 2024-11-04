package com.rbalazs.stock.service;

import com.rbalazs.stock.enums.StockAppConstants;
import com.rbalazs.stock.model.Product;
import com.rbalazs.stock.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Populates the stock database with sample data.
 */
@Service
public class PopulateStockSampleDataService implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopulateStockSampleDataService.class);


    StockRepository stockRepository;

    @Autowired
    public PopulateStockSampleDataService(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if ( CollectionUtils.isNotEmpty(stockRepository.findAll())) {
            return;
        }
        LOGGER.info("populates the stock database with some initial sample data ..");
        createProduct(StockAppConstants.PRODUCT_OFFICE_CHAIR_NAME, StockAppConstants.PRODUCT_OFFICE_CHAIR_AVAILABLE_QUANTITY);
        createProduct(StockAppConstants.PRODUCT_SOFA_NAME, StockAppConstants.PRODUCT_SOFA_AVAILABLE_QUANTITY);
    }

    private void createProduct(final String productName, final Integer availableQuantity){
        Product product = new Product(productName, availableQuantity);
        stockRepository.save(product);
    }
}