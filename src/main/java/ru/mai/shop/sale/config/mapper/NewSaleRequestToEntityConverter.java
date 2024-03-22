package ru.mai.shop.sale.config.mapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import ru.mai.shop.sale.entity.Sale;
import ru.mai.shop.sale.generated.dto.NewSaleRequest;

import java.math.BigDecimal;

public class NewSaleRequestToEntityConverter implements Converter<NewSaleRequest, Sale> {

    @Override
    public Sale convert(MappingContext<NewSaleRequest, Sale> context) {
        NewSaleRequest request = context.getSource();

        return new Sale()
                .setType(request.getType())
                .setProducts(request.getProducts())
                .setCostTotal(request.getProducts().stream()
                        .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getCount())))
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO));
    }
}
