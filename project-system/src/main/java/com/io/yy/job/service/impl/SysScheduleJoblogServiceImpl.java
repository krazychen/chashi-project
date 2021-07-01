package com.io.yy.job.service.impl;

import com.io.yy.job.entity.SysScheduleJoblog;
import com.io.yy.job.mapper.SysScheduleJoblogMapper;
import com.io.yy.job.service.SysScheduleJoblogService;
import com.io.yy.job.param.SysScheduleJoblogQueryParam;
import com.io.yy.job.vo.SysScheduleJoblogQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 定时任务日志 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2019-12-18
 */
@Slf4j
@Service
public class SysScheduleJoblogServiceImpl extends BaseServiceImpl<SysScheduleJoblogMapper, SysScheduleJoblog> implements SysScheduleJoblogService {

    @Autowired
    private SysScheduleJoblogMapper sysScheduleJoblogMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysScheduleJoblog(SysScheduleJoblog sysScheduleJoblog) throws Exception {
        return super.save(sysScheduleJoblog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysScheduleJoblog(SysScheduleJoblog sysScheduleJoblog) throws Exception {
        return super.updateById(sysScheduleJoblog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysScheduleJoblog(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysScheduleJoblogs(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysScheduleJoblogQueryVo getSysScheduleJoblogById(Serializable id) throws Exception {
        return sysScheduleJoblogMapper.getSysScheduleJoblogById(id);
    }

    @Override
    public Paging<SysScheduleJoblogQueryVo> getSysScheduleJoblogPageList(SysScheduleJoblogQueryParam sysScheduleJoblogQueryParam) throws Exception {
        Page page = setPageParam(sysScheduleJoblogQueryParam, OrderItem.desc("create_time"));
        IPage<SysScheduleJoblogQueryVo> iPage = sysScheduleJoblogMapper.getSysScheduleJoblogPageList(page, sysScheduleJoblogQueryParam);
        return new Paging(iPage);
    }

}
