package com.io.yy.system.controller;

import com.io.yy.system.entity.SysMsgRecord;
import com.io.yy.system.service.SysMsgRecordService;
import com.io.yy.system.param.SysMsgRecordQueryParam;
import com.io.yy.system.vo.SysMsgRecordQueryVo;
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
 * 系统消息记录表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Slf4j
@RestController
@RequestMapping("/sysMsgRecord")
@Api("系统消息记录表 API")
public class SysMsgRecordController extends BaseController {

    @Autowired
    private SysMsgRecordService sysMsgRecordService;

    /**
     * 添加系统消息记录表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:msg:record:add")
    @ApiOperation(value = "添加SysMsgRecord对象", notes = "添加系统消息记录表", response = ApiResult.class)
    public ApiResult<Boolean> addSysMsgRecord(@Valid @RequestBody SysMsgRecord sysMsgRecord) throws Exception {
        boolean flag = sysMsgRecordService.saveSysMsgRecord(sysMsgRecord);
        return ApiResult.result(flag);
    }

    /**
     * 修改系统消息记录表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:msg:record:update")
    @ApiOperation(value = "修改SysMsgRecord对象", notes = "修改系统消息记录表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysMsgRecord(@Valid @RequestBody SysMsgRecord sysMsgRecord) throws Exception {
        boolean flag = sysMsgRecordService.updateSysMsgRecord(sysMsgRecord);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统消息记录表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:msg:record:delete")
    @ApiOperation(value = "删除SysMsgRecord对象", notes = "删除系统消息记录表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysMsgRecord(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysMsgRecordService.deleteSysMsgRecord(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除系统消息记录表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:msg:record:delete")
    @ApiOperation(value = "批量删除SysMsgRecord对象", notes = "批量删除系统消息记录表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysMsgRecord(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysMsgRecordService.deleteSysMsgRecords(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取系统消息记录表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:msg:record:info")
    @ApiOperation(value = "获取SysMsgRecord对象详情", notes = "查看系统消息记录表", response = SysMsgRecordQueryVo.class)
    public ApiResult<SysMsgRecordQueryVo> getSysMsgRecord(@PathVariable("id") Long id) throws Exception {
        SysMsgRecordQueryVo sysMsgRecordQueryVo = sysMsgRecordService.getSysMsgRecordById(id);
        return ApiResult.ok(sysMsgRecordQueryVo);
    }

    /**
     * 系统消息记录表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:msg:record:page")
    @ApiOperation(value = "获取SysMsgRecord分页列表", notes = "系统消息记录表分页列表", response = SysMsgRecordQueryVo.class)
    public ApiResult<Paging<SysMsgRecordQueryVo>> getSysMsgRecordPageList(@Valid @RequestBody SysMsgRecordQueryParam sysMsgRecordQueryParam) throws Exception {
        Paging<SysMsgRecordQueryVo> paging = sysMsgRecordService.getSysMsgRecordPageList(sysMsgRecordQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 修改系统消息记录表状态和时间
     */
    @PostMapping("/updateByRecord/{id}")
    @RequiresPermissions("sys:msg:record:updateByRecord")
    @ApiOperation(value = "修改SysMsgRecord对象", notes = "修改系统消息记录表状态和时间", response = ApiResult.class)
    public ApiResult<Boolean> updateByRecord(@PathVariable("id") String id) throws Exception {
        boolean flag = sysMsgRecordService.updateByRecord(id);
        return ApiResult.result(flag);
    }

}

