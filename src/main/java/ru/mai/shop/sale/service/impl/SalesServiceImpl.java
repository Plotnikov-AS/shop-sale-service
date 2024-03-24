package ru.mai.shop.sale.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mai.shop.sale.entity.Sale;
import ru.mai.shop.sale.generated.dto.NewSaleRequest;
import ru.mai.shop.sale.generated.dto.PagedSalesResponse;
import ru.mai.shop.sale.generated.dto.SaleDto;
import ru.mai.shop.sale.generated.dto.SalesResponse;
import ru.mai.shop.sale.integration.kafka.service.KafkaProducer;
import ru.mai.shop.sale.repo.SalesRepo;
import ru.mai.shop.sale.service.SalesService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {
    private final SalesRepo salesRepo;
    private final ModelMapper mapper;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional
    public SaleDto createNewSale(NewSaleRequest request) {
        Sale createdSale = salesRepo.save(mapper.map(request, Sale.class));
        switch (request.getType()) {
            case STORE, DELIVERY -> kafkaProducer.sendNewSaleEvent(request.getProducts());
            case REFUND -> kafkaProducer.sendRefundEvent(request.getProducts());
            default -> throw new IllegalArgumentException("Неизвестный тип продажи: %s".formatted(request.getType()));
        }

        return mapper.map(createdSale, SaleDto.class);
    }

    @Override
    public PagedSalesResponse getAllSales(Pageable pageable) {
        Page<Sale> salesPage = salesRepo.findAll(pageable);

        return new PagedSalesResponse()
                .content(salesPage.stream()
                        .map(product -> mapper.map(product, SaleDto.class))
                        .toList())
                .pageable(pageable)
                .last(salesPage.isLast())
                .first(salesPage.isFirst())
                .empty(salesPage.isEmpty())
                .number(salesPage.getNumber())
                .numberOfElements(salesPage.getNumberOfElements())
                .size(salesPage.getSize())
                .sort(salesPage.getSort())
                .totalElements(salesPage.getTotalElements())
                .totalPages(salesPage.getTotalPages());
    }

    @Override
    public SaleDto getSale(UUID id) {
        return salesRepo.findById(id)
                .map(sale -> mapper.map(sale, SaleDto.class))
                .orElseThrow(() -> new IllegalArgumentException("Продажа с ID %s не найдена".formatted(id)));
    }

    @Override
    public void delete(UUID id) {
        salesRepo.deleteById(id);
    }
}
