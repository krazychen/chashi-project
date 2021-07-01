package com.io.yy.job.service;

import com.io.yy.job.entity.SysScheduleJoblog;
import com.io.yy.common.service.BaseService;
import com.io.yy.job.param.SysScheduleJoblogQueryParam;
import com.io.yy.job.vo.SysScheduleJoblogQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 定时任务日志 服务类
 * </pre>
 *
 * @author kris
 * @since 2019-12-18
 */
public interface SysScheduleJoblogService extends BaseService<SysScheduleJoblog> {

    /**
     * 保存
     *
     * @param sysScheduleJoblog
     * @return
     * @throws Exception
     */
    boolean saveSysScheduleJoblog(SysScheduleJoblog sysScheduleJoblog) throws Exception;

    /**
     * 修改
     *
     * @param sysScheduleJoblog
     * @return
     * @throws Exception
     */
    boolean updateSysScheduleJoblog(SysScheduleJoblog sysScheduleJoblog) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysScheduleJoblog(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysScheduleJoblogs(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysScheduleJoblogQueryVo getSysScheduleJoblogById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysScheduleJoblogQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysScheduleJoblogQueryVo> getSysScheduleJoblogPageList(SysScheduleJoblogQueryParam sysScheduleJoblogQueryParam) throws Exception;

}
