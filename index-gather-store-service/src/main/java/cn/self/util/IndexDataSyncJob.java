package cn.self.util;

import cn.self.pojo.Index;
import cn.self.service.IndexDataService;
import cn.hutool.core.date.DateUtil;
import cn.self.service.IndexService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 任务类，用于刷新Index指数和IndexData指数数据
 */
public class IndexDataSyncJob extends QuartzJobBean {

    @Autowired
    IndexService indexService;
    @Autowired
    IndexDataService indexDataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时器启动" + DateUtil.now());

        List<Index> indices = indexService.fresh();
        for (Index index:indices
             ) {
            indexDataService.fresh(index.getCode());
        }
        System.out.println("定时器结束" + DateUtil.now());
    }
}
