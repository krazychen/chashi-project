package com.io.yy.job.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.job.entity.SysScheduleJoblog;
import com.io.yy.job.param.SysScheduleJoblogQueryParam;
import com.io.yy.job.vo.SysScheduleJoblogQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 定时任务日志 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2019-12-18
 */
@Repository
public interface SysScheduleJoblogMapper extends BaseMapper<SysScheduleJoblog> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysScheduleJoblogQueryVo getSysScheduleJoblogById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysScheduleJoblogQueryParam
     * @return
     */
    IPage<SysScheduleJoblogQueryVo> getSysScheduleJoblogPageList(@Param("page") Page page, @Param("param") SysScheduleJoblogQueryParam sysScheduleJoblogQueryParam);

}
