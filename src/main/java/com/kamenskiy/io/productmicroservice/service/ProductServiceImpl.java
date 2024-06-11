package com.kamenskiy.io.productmicroservice.service;

import com.kamenskiy.io.productmicroservice.service.dto.CreateProductDto;
import com.kamenskiy.io.productmicroservice.service.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
   private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto productDto) {
        //TODO save to DB
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent event = new ProductCreatedEvent(productId, productDto.getTitle(), productDto.getPrice(),
                productDto.getQuantity());
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate
                .send("product-created-events-topic", productId, event);

        future.whenComplete((result, exception) ->{
           if (exception != null) {
               LOGGER.error("Failed to send message: {}", exception.getMessage());
           } else
                LOGGER.info("Message send successfully: {}", result.getRecordMetadata());
        });
        future.join();
        LOGGER.info("Return productId: {}", productId);
        return productId;
    }
}
