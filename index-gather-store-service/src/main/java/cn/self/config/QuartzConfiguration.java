package cn.self.config;

import cn.self.util.IndexDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *定时器配置类，每隔一分钟执行一次
 * @author Administrator
 */
@Configuration
public class QuartzConfiguration {
    /**间隔时间*/
    private static final int interval = 1;

    @Bean
    public JobDetail DataSyncJobDetail(){
        return JobBuilder.newJob(IndexDataSyncJob.class).withIdentity("IndexDataSyncJob").storeDurably().build();
    }

    /**设置刷新间隔时间为1分钟*/
    @Bean
    public Trigger DataSyncTrigger(){
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(interval).repeatForever();

        return TriggerBuilder.newTrigger().forJob(DataSyncJobDetail()).withIdentity("IndexDataSyncTrigger")
                .withSchedule(simpleScheduleBuilder).build();
    }
}
