package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock,String> {
}
