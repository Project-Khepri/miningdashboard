package org.feathercoin.monitoring.scheduler;

import org.feathercoin.monitoring.UpdateDailyProductionDataApp;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UpdateDailyFtcProductionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        UpdateDailyProductionDataApp updateDailyProductionDataApp = new UpdateDailyProductionDataApp();
        updateDailyProductionDataApp.main();
    }
}
