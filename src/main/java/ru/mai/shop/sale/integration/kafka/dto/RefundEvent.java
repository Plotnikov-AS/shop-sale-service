package ru.mai.shop.sale.integration.kafka.dto;

import lombok.Data;
import ru.mai.shop.sale.generated.dto.ProductDto;

import java.util.List;
import java.util.Set;

@Data
public class RefundEvent {
    private List<ProductDto> products;
}
