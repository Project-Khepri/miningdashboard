package org.feathercoin.monitoring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.feathercoin.monitoring.domain.FeathercoinDailyMiningData;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/org/feathercoin/monitoring/dataApplicationContext.xml"})
public class MiningQueryTest {
    private static final Log log = LogFactory.getLog(MiningQueryTest.class);
    @Autowired FeathercoinMiningRepository miningRepository;
    @Autowired FeathercoinRepository repository;


    @Test()
    @Ignore("not yet an inline mongodb setup to have it running in a CI environment")
    public void readsDate() {

        Iterable<FeathercoinDailyMiningData> all = miningRepository.findAll();
        for (FeathercoinDailyMiningData feathercoinDailyMiningData : all) {
            System.out.println(feathercoinDailyMiningData);
        }

        System.out.println("RESULT:"+repository.getMiningStats(
                new Interval(new DateTime().withDate(2013,12,01),
                new DateTime().withDate(2013,12,31))));


    }

}
