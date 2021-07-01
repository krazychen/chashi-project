package com.io.yy.job.util;

import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.entity.SysScheduleJoblog;
import com.io.yy.job.service.SysScheduleJoblogService;
import com.io.yy.job.service.impl.SysScheduleJoblogServiceImpl;
import com.io.yy.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
/**
 * 定时器执行日志记录
 */
@Slf4j
@Component
public class TaskJobLog extends QuartzJobBean {

    @Autowired
    SysScheduleJoblogService sysScheduleJoblogService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        SysScheduleJob sysScheduleJob = (SysScheduleJob)context.getMergedJobDataMap().get(SysScheduleJob.JOB_PARAM_KEY) ;
        if(sysScheduleJoblogService == null){
            sysScheduleJoblogService = (SysScheduleJoblogService) SpringContextUtil.getBean("sysScheduleJoblogService") ;
        }
        if(sysScheduleJoblogService == null){
            log.info("null ---------------------------");
        }
        // 定时器日志记录
        SysScheduleJoblog sysScheduleJoblog = new SysScheduleJoblog () ;
        sysScheduleJoblog.setJobId(sysScheduleJob.getId());
        sysScheduleJoblog.setBeanName(sysScheduleJob.getBeanName());
        sysScheduleJoblog.setParams(sysScheduleJob.getParams());
        sysScheduleJoblog.setCreateTime(new Date());
        long beginTime = System.currentTimeMillis() ;
        try {
            // 加载并执行定时器的 run 方法
            Object target = SpringContextUtil.getBean(sysScheduleJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, sysScheduleJob.getParams());
            long executeTime = System.currentTimeMillis() - beginTime;
            sysScheduleJoblog.setTimes((int)executeTime);
            sysScheduleJoblog.setStatus(0);
            sysScheduleJoblog.setCreateBy(sysScheduleJob.getCreateBy());
            sysScheduleJoblog.setUpdateBy(sysScheduleJob.getUpdateBy());
            log.info("定时器 === >> "+sysScheduleJob.getId()+":"+sysScheduleJob.getJobName()+"执行成功,耗时 === >> " + executeTime);
        } catch (Exception e){
            // 异常信息
            e.printStackTrace();
            long executeTime = System.currentTimeMillis() - beginTime;
            sysScheduleJoblog.setTimes((int)executeTime);
            sysScheduleJoblog.setStatus(1);
            sysScheduleJoblog.setError(e.getMessage());
        } finally {
            sysScheduleJoblogService.save(sysScheduleJoblog) ;
        }
    }
}
