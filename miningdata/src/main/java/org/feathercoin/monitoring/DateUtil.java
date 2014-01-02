package org.feathercoin.monitoring;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class DateUtil {
    public static Interval getIntervalForCurrentMonth(){
        return getIntervalForCurrentMonth(new DateTime().withTimeAtStartOfDay());
    }

    public static Interval getIntervalForLast30Days(){
        DateTime currentDate = new DateTime().withTimeAtStartOfDay();
        DateTime date30DaysBack = currentDate.minusDays(30).withTimeAtStartOfDay();
        return new Interval(date30DaysBack,currentDate);
    }


    public static Interval getIntervalForCurrentMonth(DateTime currentDate){
        DateTime startOfMonth = currentDate.withDayOfMonth(1).withTimeAtStartOfDay();
        return new Interval(startOfMonth,currentDate);
    }



}
