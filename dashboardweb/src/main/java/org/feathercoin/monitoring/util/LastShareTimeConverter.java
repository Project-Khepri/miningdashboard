package org.feathercoin.monitoring.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LastShareTimeConverter {
    public static String convertShareTime(Long shareTime){
        if (shareTime==null)
            return null;

        long dateAsLong = shareTime*1000;
        Date date = new Date(dateAsLong);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
