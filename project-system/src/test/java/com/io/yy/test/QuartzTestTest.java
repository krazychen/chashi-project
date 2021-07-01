package com.io.yy.test;

import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.util.ScheduleManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Job配置
 *
 * @author kris
 * @date 2019/12/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzTestTest {
    @Autowired
    ScheduleManager scheduleManager;

    @Test
    public void main() throws Exception {

        SysScheduleJob sysScheduleJob =  scheduleManager.getJobByNameAndGroup("test1","testGroup1");
        if(sysScheduleJob != null){
            scheduleManager.deleteJob(sysScheduleJob);
        }
        sysScheduleJob = new SysScheduleJob();
        sysScheduleJob.setJobName("test1");
        sysScheduleJob.setJobGroup("testGroup1");
        sysScheduleJob.setJobType("simple");
        sysScheduleJob.setBeanName("getTimeTask");
        sysScheduleJob.setParams("");
        sysScheduleJob.setCronExpression("");
        sysScheduleJob.setSimpleRepeatCount(10);
        sysScheduleJob.setSimpleUnit("milliseconds");
        sysScheduleJob.setSimpleTime(new Long(10));
        sysScheduleJob.setCreateBy("1");
        sysScheduleJob.setUpdateBy("1");

        scheduleManager.createJobByTaskLog(sysScheduleJob,null);
    }
}