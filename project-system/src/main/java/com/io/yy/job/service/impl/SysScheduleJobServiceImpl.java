package com.io.yy.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.mapper.SysScheduleJobMapper;
import com.io.yy.job.service.SysScheduleJobService;
import com.io.yy.job.param.SysScheduleJobQueryParam;
import com.io.yy.job.vo.SysScheduleJobQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
 * <pre>
 * 定时任务 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2019-12-19
 */
@Slf4j
@Service
public class SysScheduleJobServiceImpl extends BaseServiceImpl<SysScheduleJobMapper, SysScheduleJob> implements SysScheduleJobService {

    @Autowired
    private SysScheduleJobMapper sysScheduleJobMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysScheduleJob(SysScheduleJob sysScheduleJob) throws Exception {
        return super.save(sysScheduleJob);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysScheduleJob(SysScheduleJob sysScheduleJob) throws Exception {
        return super.updateById(sysScheduleJob);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysScheduleJob(String id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysScheduleJobs(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysScheduleJobQueryVo getSysScheduleJobById(String id) throws Exception {
        return sysScheduleJobMapper.getSysScheduleJobById(id);
    }

    @Override
    public Paging<SysScheduleJobQueryVo> getSysScheduleJobPageList(SysScheduleJobQueryParam sysScheduleJobQueryParam) throws Exception {
        Page page = setPageParam(sysScheduleJobQueryParam, OrderItem.desc("create_time"));
        IPage<SysScheduleJobQueryVo> iPage = sysScheduleJobMapper.getSysScheduleJobPageList(page, sysScheduleJobQueryParam);
        return new Paging(iPage);
    }

    @Override
    public SysScheduleJob findScheduleByJobNameAndGroupName(String jobName, String group){
        QueryWrapper<SysScheduleJob> sysScheduleJobQueryWrapper = new QueryWrapper<>();
        sysScheduleJobQueryWrapper.eq("job_name",jobName).eq("job_group",group);
        return sysScheduleJobMapper.selectOne(sysScheduleJobQueryWrapper);
    }
}
