package ru.mai.shop.sale.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mai.shop.sale.entity.Sale;

import java.util.UUID;

public interface SalesRepo extends JpaRepository<Sale, UUID> {
}
