package com.io.yy.job.service;

import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.common.service.BaseService;
import com.io.yy.job.param.SysScheduleJobQueryParam;
import com.io.yy.job.vo.SysScheduleJobQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 定时任务 服务类
 * </pre>
 *
 * @author kris
 * @since 2019-12-19
 */
public interface SysScheduleJobService extends BaseService<SysScheduleJob> {

    /**
     * 保存
     *
     * @param sysScheduleJob
     * @return
     * @throws Exception
     */
    boolean saveSysScheduleJob(SysScheduleJob sysScheduleJob) throws Exception;

    /**
     * 修改
     *
     * @param sysScheduleJob
     * @return
     * @throws Exception
     */
    boolean updateSysScheduleJob(SysScheduleJob sysScheduleJob) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysScheduleJob(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysScheduleJobs(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysScheduleJobQueryVo getSysScheduleJobById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysScheduleJobQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysScheduleJobQueryVo> getSysScheduleJobPageList(SysScheduleJobQueryParam sysScheduleJobQueryParam) throws Exception;

    /**
     * 根据jobname和group获取SysScheduleJob
     *
     * @param jobName
     * @param group
     * @return
     * @throws Exception
     */
    public SysScheduleJob findScheduleByJobNameAndGroupName(String jobName, String group);
}
