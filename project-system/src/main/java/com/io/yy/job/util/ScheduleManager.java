package com.io.yy.job.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.job.entity.ScheduleStatusEnum;
import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.entity.SysScheduleJoblog;
import com.io.yy.job.mapper.SysScheduleJobMapper;
import com.io.yy.job.service.SysScheduleJobService;
import com.io.yy.job.service.impl.SysScheduleJoblogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by shirukai on 2018/9/7
 * 定时任务管理器
 */
@Slf4j
@Component
public class ScheduleManager {

    @Autowired
    Scheduler scheduler;
    
    @Autowired
    SysScheduleJobService sysScheduleJobService;

    @Autowired
    SysScheduleJoblogServiceImpl sysScheduleJoblogServiceImpl;

    @Autowired
    private SysScheduleJobMapper sysScheduleJobMapper;

    /**
     * 根据SysScheduleJob 的job type创建 simple schedule job 或者cron schedule job
     *
     * @param sysScheduleJob   数
     * @param jobDataMap 数据
     * @return Schedule
     */
    public SysScheduleJob createJobByTaskLog(
                                    SysScheduleJob sysScheduleJob,
                                    JobDataMap jobDataMap) {
        Trigger trigger = getTrigger(sysScheduleJob);
        return createJobByTaskLog( sysScheduleJob, jobDataMap, trigger);
    }

    /**
     * 创建Job
     *
     * @param sd         调度参数
     * @param jobDataMap 数据
     * @param trigger    trigger
     * @return Schedule
     */
    private SysScheduleJob createJobByTaskLog(
            SysScheduleJob sd,
            JobDataMap jobDataMap,
            Trigger trigger
    ) {
        String jobName = sd.getJobName();
        String group = sd.getJobGroup();
        //判断记录在数据库是否存在

        SysScheduleJob sysScheduleJob = sysScheduleJobService.findScheduleByJobNameAndGroupName(jobName, group);
        if (sysScheduleJob == null) {
            sysScheduleJob = new SysScheduleJob();
        } else {
            throw new RuntimeException("Schedule job already exists.");
        }
        String scheduleId = UUID.randomUUID().toString();
        try {
            if (jobDataMap == null) {
                jobDataMap = new JobDataMap();
            }
            jobDataMap.put("id", scheduleId);
            //创建JobDetail
            sysScheduleJob.setId(scheduleId);
            sysScheduleJob.setStatus(ScheduleStatusEnum.ACTIVATED.getState());
            sysScheduleJob.setJobName(jobName);
            sysScheduleJob.setJobGroup(group);
            sysScheduleJob.setBeanName(sd.getBeanName());
            sysScheduleJob.setRemarks(sd.getRemarks());
            sysScheduleJob.setJobType(sd.getJobType());
            sysScheduleJob.setCronExpression(sd.getCronExpression());
            sysScheduleJob.setSimpleRepeatCount(sd.getSimpleRepeatCount());
            sysScheduleJob.setSimpleTime(sd.getSimpleTime());
            sysScheduleJob.setSimpleUnit(sd.getSimpleUnit());
            sysScheduleJob.setStartTime(sd.getStartTime());
            sysScheduleJob.setEndTime(sd.getEndTime());
            sysScheduleJob.setParams(sd.getParams());
//            schedule.setRecord(0);
            jobDataMap.put(SysScheduleJob.JOB_PARAM_KEY,sysScheduleJob);

            JobDetail jobDetail = JobBuilder.newJob(TaskJobLog.class).withIdentity(jobName, group).usingJobData(jobDataMap).build();

            //保存记录信息
            boolean isSaved = sysScheduleJobService.save(sysScheduleJob);
            //调度执行定时任务
            scheduler.scheduleJob(jobDetail, trigger);

            // 如果该定时器处于暂停状态
            if (sysScheduleJob.getStatus() == ScheduleStatusEnum.INACTIVATED.getState()){
                pauseJob(sysScheduleJob.getId()) ;
            }
        } catch (Exception e) {
            log.error("Create schedule job error:{}", e.getMessage());
            throw new RuntimeException(e);
        }
        return sysScheduleJob;
    }


    /**
     * 根据SysScheduleJob 的job type创建 simple schedule job 或者cron schedule job
     *
     * @param jobClass   job class
     * @param sysScheduleJob   数
     * @param jobDataMap 数据
     * @return Schedule
     */
    public SysScheduleJob createJob(Class<? extends Job> jobClass,
                                          SysScheduleJob sysScheduleJob,
                                          JobDataMap jobDataMap) {
        Trigger trigger = getTrigger(sysScheduleJob);
        return createJob(jobClass, sysScheduleJob, jobDataMap, trigger);
    }

    /**
     * 创建Job
     *
     * @param jobClass   要调度的类名
     * @param sd         调度参数
     * @param jobDataMap 数据
     * @param trigger    trigger
     * @return Schedule
     */
    private SysScheduleJob createJob(
            Class<? extends Job> jobClass,
            SysScheduleJob sd,
            JobDataMap jobDataMap,
            Trigger trigger
    ) {
        String jobName = sd.getJobName();
        String group = sd.getJobGroup();
        //判断记录在数据库是否存在

        SysScheduleJob sysScheduleJob = sysScheduleJobService.findScheduleByJobNameAndGroupName(jobName, group);
        if (sysScheduleJob == null) {
            sysScheduleJob = new SysScheduleJob();
        } else {
            throw new RuntimeException("Schedule job already exists.");
        }
        String scheduleId = UUID.randomUUID().toString();
        try {
            if (jobDataMap == null) {
                jobDataMap = new JobDataMap();
            }
            jobDataMap.put("id", scheduleId);
            //创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, group).usingJobData(jobDataMap).build();
            sysScheduleJob.setId(scheduleId);
            sysScheduleJob.setStatus(ScheduleStatusEnum.ACTIVATED.getState());
            sysScheduleJob.setJobName(jobName);
            sysScheduleJob.setJobGroup(group);
            sysScheduleJob.setRemarks(JSON.toJSONString(sd));
            sysScheduleJob.setJobType(sd.getJobType());
            sysScheduleJob.setCronExpression(sd.getCronExpression());
            sysScheduleJob.setSimpleRepeatCount(sd.getSimpleRepeatCount());
            sysScheduleJob.setSimpleTime(sd.getSimpleTime());
            sysScheduleJob.setSimpleUnit(sd.getSimpleUnit());
            sysScheduleJob.setStartTime(sd.getStartTime());
            sysScheduleJob.setEndTime(sd.getEndTime());
//            schedule.setRecord(0);
            //保存记录信息
            boolean isSaved = sysScheduleJobService.save(sysScheduleJob);
            //调度执行定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("Create schedule job error:{}", e.getMessage());
            throw new RuntimeException(e);
        }
        return sysScheduleJob;
    }

    /**
     * 根据SysScheduleJob 的job type更新simple job 或者 cron schedule job
     *
     * @param jobId jobId
     * @param newSysScheduleJob
     * @return Schedule
     */
    public SysScheduleJob updateJob(String jobId, SysScheduleJob newSysScheduleJob) {
        SysScheduleJob oldSysScheduleJob = getJob(jobId);
        return updateJob(oldSysScheduleJob, newSysScheduleJob);
    }


    public SysScheduleJob updateJob(SysScheduleJob oldSysScheduleJob, SysScheduleJob newSysScheduleJob) {
        try {
            String jobName = oldSysScheduleJob.getJobName();
            String groupName = oldSysScheduleJob.getJobGroup();
            JobKey jobKey = new JobKey(jobName, groupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            //先删除
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
            sysScheduleJobMapper.deleteById(oldSysScheduleJob.getId());

            //重新创建
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("id", oldSysScheduleJob.getId());
            newSysScheduleJob.setId(oldSysScheduleJob.getId());
            newSysScheduleJob.setRemarks(JSON.toJSONString(newSysScheduleJob));
            newSysScheduleJob.setStatus(ScheduleStatusEnum.ACTIVATED.getState());

            jobDataMap.put(SysScheduleJob.JOB_PARAM_KEY,newSysScheduleJob);
            JobDetail newJobDetail = JobBuilder.newJob(TaskJobLog.class).withIdentity(jobName, groupName).usingJobData(jobDataMap).build();
            Trigger trigger = getTrigger(newSysScheduleJob);
            scheduler.scheduleJob(newJobDetail, trigger);

            jobDetail.getJobDataMap().put(SysScheduleJob.JOB_PARAM_KEY, newSysScheduleJob);
            sysScheduleJobService.save(newSysScheduleJob);
        } catch (SchedulerException e) {
            log.error("Update simple schedule job error:{}", e.getMessage());
        }
        return oldSysScheduleJob;
    }

    public SysScheduleJob getJob(String jobId) {
        SysScheduleJob sysScheduleJob = sysScheduleJobService.getById(jobId);
        if (sysScheduleJob == null) {
            throw new RuntimeException("Schedule job does not exist");
        }
        return sysScheduleJob;
    }


    public SysScheduleJob getJobByJobName(String jobName){
        SysScheduleJob sysScheduleJob = sysScheduleJobMapper.getJobByJobName(jobName);
        return sysScheduleJob;
    }

    /**
     * 暂停某个job
     *
     * @param jobId
     */
    public SysScheduleJob pauseJob(String jobId) {
        SysScheduleJob sysScheduleJob = getJob(jobId);
        return pauseJob(sysScheduleJob);
    }

    public SysScheduleJob pauseJob(SysScheduleJob sysScheduleJob) {
        JobKey jobKey = new JobKey(sysScheduleJob.getJobName(), sysScheduleJob.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);
            sysScheduleJob.setStatus(ScheduleStatusEnum.INACTIVATED.getState());
            sysScheduleJobService.updateById(sysScheduleJob);
        } catch (SchedulerException e) {
            log.error("Pause schedule job error:{}", e.getMessage());
        }
        return sysScheduleJob;
    }

    /**
     * 恢复某个job
     *
     * @param jobId id
     */
    public SysScheduleJob resumeJob(String jobId) {
        SysScheduleJob sysScheduleJob = getJob(jobId);
        return resumeJob(sysScheduleJob);
    }

    public SysScheduleJob resumeJob(SysScheduleJob sysScheduleJob) {
        JobKey jobKey = new JobKey(sysScheduleJob.getJobName(), sysScheduleJob.getJobGroup());
        try {
            scheduler.resumeJob(jobKey);
            sysScheduleJob.setStatus(ScheduleStatusEnum.ACTIVATED.getState());
            sysScheduleJobService.updateById(sysScheduleJob);
        } catch (SchedulerException e) {
            log.error("Resume schedule job error:{}", e.getMessage());
        }
        return sysScheduleJob;
    }

    /**
     * 删除 job
     *
     * @param jobId id
     */
    public boolean deleteJob(String jobId) {
        QueryWrapper<SysScheduleJoblog> scheduleJoblogQueryWrapper = new QueryWrapper<>();
        scheduleJoblogQueryWrapper.eq("job_id",jobId);
        int count = sysScheduleJoblogServiceImpl.count(scheduleJoblogQueryWrapper);
        if(count > 0){
            throw new BusinessException("该任务已产生调度日志,不能删除!");
        }
        SysScheduleJob sysScheduleJob = getJob(jobId);
        return deleteJob(sysScheduleJob);
    }

    public boolean deleteJob(SysScheduleJob sysScheduleJob) {
        JobKey jobKey = new JobKey(sysScheduleJob.getJobName(), sysScheduleJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
            return sysScheduleJobService.deleteSysScheduleJob(sysScheduleJob.getId());
        } catch (Exception e) {
            log.error("Delete schedule job error:{}", e.getMessage());
        }
        return false;
    }

    public SysScheduleJob getJobByNameAndGroup(String name, String group) {
        SysScheduleJob sysScheduleJob = sysScheduleJobService.findScheduleByJobNameAndGroupName(name, group);
        if (sysScheduleJob == null) {
            throw new RuntimeException("Schedule job does not exist");
        }
        return sysScheduleJob;
    }

    /**
     * 构建 SimpleScheduleBuilder
     *
     * @param unit 周期参数 - 间隔单位
     * @param time 周期参数 - 间隔时间
     * @param repeatCount 重复次数
     * @return SimpleScheduleBuilder
     */
    private SimpleScheduleBuilder getSimpeScheduleBuilder(String unit, long time, int repeatCount) {
        SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule();
        switch (unit) {
            case "milliseconds":
                ssb.withIntervalInMilliseconds(time);
                break;
            case "seconds":
                ssb.withIntervalInSeconds((int) time);
                break;
            case "minutes":
                ssb.withIntervalInMinutes((int) time);
                break;
            case "hours":
                ssb.withIntervalInHours((int) time);
                break;
            case "days":
                ssb.withIntervalInHours((int) time * 24);
                break;
            default:
                break;
        }
        if(repeatCount>0){
            ssb.withRepeatCount(repeatCount);
        }else{
            ssb.repeatForever();
        }
        return ssb;
    }

    /**
     * 根据scheduleJob对象的job类型 构建 SimpleTrigger或者 CronTrigger
     *
     * @param sysScheduleJob 参数
     * @return Trigger
     */
    private Trigger getTrigger(SysScheduleJob sysScheduleJob) {
        String jobName = sysScheduleJob.getJobName();
        String group = sysScheduleJob.getJobGroup();
        if(sysScheduleJob.getJobType().equals("simple")){
            int repeatCount = sysScheduleJob.getSimpleRepeatCount()==null?0:sysScheduleJob.getSimpleRepeatCount();
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger()
                    //设置jobName和group
                    .withIdentity(jobName, group)
                    //设置Schedule方式
                    .withSchedule(getSimpeScheduleBuilder(sysScheduleJob.getSimpleUnit(),sysScheduleJob.getSimpleTime(), repeatCount));
            if (sysScheduleJob.getStartTime() != null) {
                //设置起始时间
                triggerBuilder.startAt(sysScheduleJob.getStartTime());
            } else {
                triggerBuilder.startNow();
            }
            if (sysScheduleJob.getEndTime() != null) {
                //设置终止时间
                triggerBuilder.endAt(sysScheduleJob.getEndTime());
            }
            return triggerBuilder.build();
        }else{
            CronScheduleBuilder scb = CronScheduleBuilder.cronSchedule(sysScheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(sysScheduleJob.getJobName(), sysScheduleJob.getJobGroup())
                    .withSchedule(scb);
            if (sysScheduleJob.getStartTime() != null) {
                triggerBuilder.startAt(sysScheduleJob.getStartTime());
            } else {
                triggerBuilder.startNow();
            }
            if (sysScheduleJob.getEndTime() != null) {
                triggerBuilder.endAt(sysScheduleJob.getEndTime());
            }
            return triggerBuilder.build();
        }
    }
}
