package ru.mai.shop.sale.integration.kafka.service;

import ru.mai.shop.sale.generated.dto.ProductDto;

import java.util.List;

public interface KafkaProducer {

    void sendNewSaleEvent(List<ProductDto> products);

    void sendRefundEvent(List<ProductDto> products);
}
