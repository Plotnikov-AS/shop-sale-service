package ru.mai.shop.sale.service;

import org.springframework.data.domain.Pageable;
import ru.mai.shop.sale.generated.dto.NewSaleRequest;
import ru.mai.shop.sale.generated.dto.PagedSalesResponse;
import ru.mai.shop.sale.generated.dto.SaleDto;
import ru.mai.shop.sale.generated.dto.SalesResponse;

import java.util.UUID;

public interface SalesService {
    SaleDto createNewSale(NewSaleRequest newSaleRequest);

    PagedSalesResponse getAllSales(Pageable pageable);

    SaleDto getSale(UUID id);
}
