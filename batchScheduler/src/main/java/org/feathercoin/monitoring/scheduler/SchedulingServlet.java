package org.feathercoin.monitoring.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SchedulingServlet extends HttpServlet {

    public static final String ORG_QUARTZ_IMPL_STD_SCHEDULER_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

    @Override
    public void init(ServletConfig cfg) {
        Scheduler quartzScheduler = getScheduler(cfg);

        try {
            quartzScheduler.start();
            JobDetail jobDetail = newJob(UpdateDailyFtcProductionJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            JobDetail jobDetailEvery2Hours = newJob(UpdateDailyFtcProductionJob.class)
                    .withIdentity("job2", "group1")
                    .build();

            CronTrigger cronOnceDailyTrigger = newTrigger()
                    .withIdentity("cronOnceDailyTrigger", "group1")
                    .withSchedule(cronSchedule("0 45 23 * * ?"))
                    .build();

            Trigger every2HoursTrigger = newTrigger()
                    .withIdentity("every2HoursTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(2)
                            .repeatForever())
                    .build();

            quartzScheduler.scheduleJob(jobDetailEvery2Hours, every2HoursTrigger);
            quartzScheduler.scheduleJob(jobDetail, cronOnceDailyTrigger);



        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private Scheduler getScheduler(ServletConfig cfg) {
        String key = ORG_QUARTZ_IMPL_STD_SCHEDULER_FACTORY_KEY;
        ServletContext servletContext = cfg.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext.getAttribute(key);
        Scheduler quartzScheduler = null;
        try {
            quartzScheduler = factory.getScheduler("MyQuartzScheduler");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return quartzScheduler;
    }

    @Override
    public void destroy() {
        try {
            getScheduler(getServletConfig()).shutdown();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
