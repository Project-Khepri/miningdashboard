package org.feathercoin.monitoring;

import org.feathercoin.monitoring.domain.FeathercoinDailyStats;
import org.springframework.data.repository.CrudRepository;

public interface FeathercoinDailyStatsRepository extends CrudRepository<FeathercoinDailyStats,String> {
}
