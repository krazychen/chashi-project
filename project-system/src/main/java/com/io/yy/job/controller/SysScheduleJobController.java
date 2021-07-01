package com.io.yy.job.controller;

import com.io.yy.job.entity.SysScheduleJob;
import com.io.yy.job.service.SysScheduleJobService;
import com.io.yy.job.param.SysScheduleJobQueryParam;
import com.io.yy.job.util.ScheduleManager;
import com.io.yy.job.vo.SysScheduleJobQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

import com.io.yy.common.vo.Paging;
import com.io.yy.common.param.IdParam;

/**
 * <pre>
 * 定时任务 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2019-12-19
 */
@Slf4j
@RestController
@RequestMapping("/sysScheduleJob")
@Api("定时任务 API")
public class SysScheduleJobController extends BaseController {

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private SysScheduleJobService sysScheduleJobService;

    /**
     * 添加定时任务
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:schedule:job:add")
    @ApiOperation(value = "添加SysScheduleJob对象", notes = "添加定时任务", response = ApiResult.class)
    public ApiResult<Boolean> addSysScheduleJob(@Valid @RequestBody SysScheduleJob sysScheduleJob) throws Exception {
        SysScheduleJob saved = scheduleManager.createJobByTaskLog(sysScheduleJob,null);
        if(saved!=null){
            return ApiResult.result(true);
        }
        return ApiResult.result(false);
    }

    /**
     * 修改定时任务
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:schedule:job:update")
    @ApiOperation(value = "修改SysScheduleJob对象", notes = "修改定时任务", response = ApiResult.class)
    public ApiResult<Boolean> updateSysScheduleJob(@Valid @RequestBody SysScheduleJob sysScheduleJob) throws Exception {
        SysScheduleJob updated = scheduleManager.updateJob(sysScheduleJob,sysScheduleJob);
        if(updated!=null){
            return ApiResult.result(true);
        }
        return ApiResult.result(false);
    }

    /**
     * 删除定时任务
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:schedule:job:delete")
    @ApiOperation(value = "删除SysScheduleJob对象", notes = "删除定时任务", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysScheduleJob(@PathVariable("id") String id) throws Exception {
        boolean flag = scheduleManager.deleteJob(id);
        return ApiResult.result(flag);
    }

//    /**
//     * 批量删除定时任务
//     */
//    @PostMapping("/delete")
//    @RequiresPermissions("sys:schedule:job:delete")
//    @ApiOperation(value = "批量删除SysScheduleJob对象", notes = "批量删除定时任务", response = ApiResult.class)
//    public ApiResult<Boolean> deleteSysScheduleJob(@Valid @RequestBody List<String> idList) throws Exception {
//        boolean flag = sysScheduleJobService.deleteSysScheduleJobs(idList);
//        return ApiResult.result(flag);
//    }

    /**
     * 获取定时任务
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:schedule:job:info")
    @ApiOperation(value = "获取SysScheduleJob对象详情", notes = "查看定时任务", response = SysScheduleJobQueryVo.class)
    public ApiResult<SysScheduleJob> getSysScheduleJob(@PathVariable("id") String id) throws Exception {
        SysScheduleJob sysScheduleJob = scheduleManager.getJob(id);
        return ApiResult.ok(sysScheduleJob);
    }

    /**
     * 定时任务分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:schedule:job:page")
    @ApiOperation(value = "获取SysScheduleJob分页列表", notes = "定时任务分页列表", response = SysScheduleJobQueryVo.class)
    public ApiResult<Paging<SysScheduleJobQueryVo>> getSysScheduleJobPageList(@Valid @RequestBody SysScheduleJobQueryParam sysScheduleJobQueryParam) throws Exception {
        Paging<SysScheduleJobQueryVo> paging = sysScheduleJobService.getSysScheduleJobPageList(sysScheduleJobQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pauseJob/{id}")
    @RequiresPermissions("sys:schedule:job:pauseJob")
    @ApiOperation(value = "暂停SysScheduleJob对象", notes = "暂停定时任务", response = ApiResult.class)
    public ApiResult<Boolean> pauseJob(@PathVariable("id") String id) throws Exception {
        SysScheduleJob pauseJob = scheduleManager.pauseJob(id);
        if(pauseJob != null){
            return ApiResult.result(true);
        }
        return ApiResult.result(false);
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resumeJob/{id}")
    @RequiresPermissions("sys:schedule:job:resumeJob")
    @ApiOperation(value = "恢复SysScheduleJob对象", notes = "恢复定时任务", response = ApiResult.class)
    public ApiResult<Boolean> resumeJob(@PathVariable("id") String id) throws Exception {
        SysScheduleJob resumeJob = scheduleManager.resumeJob(id);
        if(resumeJob != null){
            return ApiResult.result(true);
        }
        return ApiResult.result(false);
    }

    /**
     * 批量暂停定时任务
     */
    @PostMapping("/batchPauseJob/{ids}")
    @RequiresPermissions("sys:schedule:job:pauseJob")
    @ApiOperation(value = "暂停SysScheduleJob对象", notes = "暂停定时任务", response = ApiResult.class)
    public ApiResult<Boolean> batchPauseJob(@PathVariable("ids") String ids) throws Exception {
            SysScheduleJob pauseJob = null;
            String[] split = ids.split(",");
            for(String id : split){
                pauseJob = scheduleManager.pauseJob(id);
            }
        if(pauseJob != null){
            return ApiResult.result(true);
        }
        return ApiResult.result(false);
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/batchResumeJob/{ids}")
    @RequiresPermissions("sys:schedule:job:resumeJob")
    @ApiOperation(value = "恢复SysScheduleJob对象", notes = "恢复定时任务", response = ApiResult.class)
    public ApiResult<Boolean> batchResumeJob(@PathVariable("ids") String ids) throws Exception {
        SysScheduleJob resumeJob = null;
        String[] jobIds = ids.split(",");
        for(String id : jobIds){
            resumeJob = scheduleManager.resumeJob(id);
        }
        if(resumeJob != null){
            return ApiResult.result(true);
        }
        return ApiResult.result(false);
    }

}

