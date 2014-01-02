package org.feathercoin.monitoring;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {
    @Test
    public void testGetIntervalForCurrentMonth() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object[][] testDates = {
            {new DateTime().withDate(2013,12,30),"2013-12-01","2013-12-30"},
            {new DateTime().withDate(2013,12,01),"2013-12-01","2013-12-01"},
            {new DateTime().withDate(2013,1,23),"2013-01-01","2013-01-23"},
            {new DateTime().withDate(2013,1,31),"2013-01-01","2013-01-31"},
        };

        for (Object[] testData : testDates) {
            Interval result = DateUtil.getIntervalForCurrentMonth((DateTime)testData[0]);

            assertEquals(testData[1],sdf.format(result.getStart().toDate()));
            assertEquals(testData[2],sdf.format(result.getEnd().toDate()));
        }


    }
}
