package com.io.yy.job.controller;

import com.io.yy.job.entity.SysScheduleJoblog;
import com.io.yy.job.service.SysScheduleJoblogService;
import com.io.yy.job.param.SysScheduleJoblogQueryParam;
import com.io.yy.job.vo.SysScheduleJoblogQueryVo;
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
 * 定时任务日志 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2019-12-18
 */
@Slf4j
@RestController
@RequestMapping("/sysScheduleJobLog")
@Api("定时任务日志 API")
public class SysScheduleJoblogController extends BaseController {

    @Autowired
    private SysScheduleJoblogService sysScheduleJoblogService;

    /**
     * 添加定时任务日志
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:schedule:joblog:add")
    @ApiOperation(value = "添加SysScheduleJoblog对象", notes = "添加定时任务日志", response = ApiResult.class)
    public ApiResult<Boolean> addSysScheduleJoblog(@Valid @RequestBody SysScheduleJoblog sysScheduleJoblog) throws Exception {
        boolean flag = sysScheduleJoblogService.saveSysScheduleJoblog(sysScheduleJoblog);
        return ApiResult.result(flag);
    }

    /**
     * 修改定时任务日志
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:schedule:joblog:update")
    @ApiOperation(value = "修改SysScheduleJoblog对象", notes = "修改定时任务日志", response = ApiResult.class)
    public ApiResult<Boolean> updateSysScheduleJoblog(@Valid @RequestBody SysScheduleJoblog sysScheduleJoblog) throws Exception {
        boolean flag = sysScheduleJoblogService.updateSysScheduleJoblog(sysScheduleJoblog);
        return ApiResult.result(flag);
    }

    /**
     * 删除定时任务日志
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:schedule:joblog:delete")
    @ApiOperation(value = "删除SysScheduleJoblog对象", notes = "删除定时任务日志", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysScheduleJoblog(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysScheduleJoblogService.deleteSysScheduleJoblog(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除定时任务日志
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:schedule:joblog:delete")
    @ApiOperation(value = "批量删除SysScheduleJoblog对象", notes = "批量删除定时任务日志", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysScheduleJoblog(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysScheduleJoblogService.deleteSysScheduleJoblogs(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取定时任务日志
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:schedule:joblog:info")
    @ApiOperation(value = "获取SysScheduleJoblog对象详情", notes = "查看定时任务日志", response = SysScheduleJoblogQueryVo.class)
    public ApiResult<SysScheduleJoblogQueryVo> getSysScheduleJoblog(@PathVariable("id") Long id) throws Exception {
        SysScheduleJoblogQueryVo sysScheduleJoblogQueryVo = sysScheduleJoblogService.getSysScheduleJoblogById(id);
        return ApiResult.ok(sysScheduleJoblogQueryVo);
    }

    /**
     * 定时任务日志分页列表
     */
    @PostMapping("/getLogPageList")
    @RequiresPermissions("sys:schedule:jobLog:page")
    @ApiOperation(value = "获取SysScheduleJoblog分页列表", notes = "定时任务日志分页列表", response = SysScheduleJoblogQueryVo.class)
    public ApiResult<Paging<SysScheduleJoblogQueryVo>> getSysScheduleJoblogPageList(@Valid @RequestBody SysScheduleJoblogQueryParam sysScheduleJoblogQueryParam) throws Exception {
        Paging<SysScheduleJoblogQueryVo> paging = sysScheduleJoblogService.getSysScheduleJoblogPageList(sysScheduleJoblogQueryParam);
        return ApiResult.ok(paging);
    }

}

