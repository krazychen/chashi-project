package com.io.yy.job.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.param.SysScheduleJobQueryParam;
import com.io.yy.job.vo.SysScheduleJobQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 定时任务 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2019-12-19
 */
@Repository
public interface SysScheduleJobMapper extends BaseMapper<SysScheduleJob> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysScheduleJobQueryVo getSysScheduleJobById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysScheduleJobQueryParam
     * @return
     */
    IPage<SysScheduleJobQueryVo> getSysScheduleJobPageList(@Param("page") Page page, @Param("param") SysScheduleJobQueryParam sysScheduleJobQueryParam);

    /**
     * 根据job名称获取job
     * @param jobName
     * @return
     */
    SysScheduleJob getJobByJobName(String jobName);
}
