package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.springframework.data.repository.CrudRepository;

public interface FeathercoinMiningRepository extends CrudRepository<FeathercoinDailyMiningData,String> {
}
