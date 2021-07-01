package com.io.yy.system.controller;

import com.io.yy.system.entity.SysUserOffice;
import com.io.yy.system.param.SysUserOfficeQueryParam;
import com.io.yy.system.service.SysUserOfficeService;
import com.io.yy.system.vo.SysUserOfficeQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 用户和机构关联表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
@Slf4j
@RestController
@RequestMapping("/sysUserOffice")
@Api("用户和机构关联表 API")
public class SysUserOfficeController extends BaseController {

    @Autowired
    private SysUserOfficeService sysUserOfficeService;

    /**
     * 添加用户和机构关联表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:user:office:add")
    @ApiOperation(value = "添加SysUserOffice对象", notes = "添加用户和机构关联表", response = ApiResult.class)
    public ApiResult<Boolean> addSysUserOffice(@Valid @RequestBody SysUserOffice sysUserOffice) throws Exception {
        boolean flag = sysUserOfficeService.saveSysUserOffice(sysUserOffice);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户和机构关联表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:office:update")
    @ApiOperation(value = "修改SysUserOffice对象", notes = "修改用户和机构关联表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysUserOffice(@Valid @RequestBody SysUserOffice sysUserOffice) throws Exception {
        boolean flag = sysUserOfficeService.updateSysUserOffice(sysUserOffice);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户和机构关联表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:user:office:delete")
    @ApiOperation(value = "删除SysUserOffice对象", notes = "删除用户和机构关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUserOffice(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserOfficeService.deleteSysUserOffice(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除用户和机构关联表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:office:delete")
    @ApiOperation(value = "批量删除SysUserOffice对象", notes = "批量删除用户和机构关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUserOffice(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysUserOfficeService.deleteSysUserOffices(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取用户和机构关联表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:user:office:info")
    @ApiOperation(value = "获取SysUserOffice对象详情", notes = "查看用户和机构关联表", response = SysUserOfficeQueryVo.class)
    public ApiResult<SysUserOfficeQueryVo> getSysUserOffice(@PathVariable("id") Long id) throws Exception {
        SysUserOfficeQueryVo sysUserOfficeQueryVo = sysUserOfficeService.getSysUserOfficeById(id);
        return ApiResult.ok(sysUserOfficeQueryVo);
    }

    /**
     * 用户和机构关联表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:user:office:page")
    @ApiOperation(value = "获取SysUserOffice分页列表", notes = "用户和机构关联表分页列表", response = SysUserOfficeQueryVo.class)
    public ApiResult<Paging<SysUserOfficeQueryVo>> getSysUserOfficePageList(@Valid @RequestBody SysUserOfficeQueryParam sysUserOfficeQueryParam) throws Exception {
        Paging<SysUserOfficeQueryVo> paging = sysUserOfficeService.getSysUserOfficePageList(sysUserOfficeQueryParam);
        return ApiResult.ok(paging);
    }

}

