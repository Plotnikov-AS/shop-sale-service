package ru.mai.shop.sale.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.shop.sale.generated.api.SaleApi;
import ru.mai.shop.sale.generated.dto.NewSaleRequest;
import ru.mai.shop.sale.generated.dto.PagedSalesResponse;
import ru.mai.shop.sale.generated.dto.SaleDto;
import ru.mai.shop.sale.generated.dto.SalesResponse;
import ru.mai.shop.sale.service.SalesService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SalesController implements SaleApi {
    private final SalesService salesService;

    @Override
    public ResponseEntity<SaleDto> createNewSale(NewSaleRequest newSaleRequest) {
        return ResponseEntity.ok(salesService.createNewSale(newSaleRequest));
    }

    @Override
    public ResponseEntity<PagedSalesResponse> getAllSales(Pageable pageable) {
        return ResponseEntity.ok(salesService.getAllSales(pageable));
    }

    @Override
    public ResponseEntity<SaleDto> getSaleById(UUID id) {
        return ResponseEntity.ok(salesService.getSale(id));
    }
}
