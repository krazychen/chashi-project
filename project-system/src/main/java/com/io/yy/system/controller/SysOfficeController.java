package com.io.yy.system.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysOffice;
import com.io.yy.system.param.SysOfficeQueryParam;
import com.io.yy.system.service.SysOfficeService;
import com.io.yy.system.vo.SysOfficeQueryVo;
import com.io.yy.system.vo.SysOfficeTreeQueryVo;
import com.io.yy.system.vo.SysOfficeUserTreeQueryVo;
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
 * 组织机构表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-23
 */
@Slf4j
@RestController
@RequestMapping("/sysOffice")
@Api("组织机构表 API")
public class SysOfficeController extends BaseController {

    @Autowired
    private SysOfficeService sysOfficeService;

    /**
     * 添加组织机构表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:office:add")
    @ApiOperation(value = "添加SysOffice对象", notes = "添加组织机构表", response = ApiResult.class)
    public ApiResult<Boolean> addSysOffice(@Valid @RequestBody SysOffice sysOffice) throws Exception {
        boolean flag = sysOfficeService.saveSysOffice(sysOffice);

        return ApiResult.result(flag);
    }

    /**
     * 修改组织机构表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:office:update")
    @ApiOperation(value = "修改SysOffice对象", notes = "修改组织机构表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysOffice(@Valid @RequestBody SysOffice sysOffice) throws Exception {
        boolean flag = sysOfficeService.updateSysOffice(sysOffice);
        return ApiResult.result(flag);
    }

    /**
     * 修改组织机构装态
     */
    @PostMapping("/updateBystatus/{officeCode}")
    @RequiresPermissions("sys:office:updateBystatus")
    @ApiOperation(value = "修改SysOffice对象状态", notes = "修改组织机构表", response = ApiResult.class)
    public ApiResult<Boolean> updateBystatus(@PathVariable("officeCode") String id) throws Exception {
        boolean flag = sysOfficeService.updateBystatus(id);
        return ApiResult.result(flag);
    }

    /**
     * 删除组织机构表
     */
    @PostMapping("/delete/{officeCode}")
    @RequiresPermissions("sys:office:delete")
    @ApiOperation(value = "删除SysOffice对象", notes = "删除组织机构表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysOffice(@PathVariable("officeCode") String id) throws Exception {
        boolean flag = sysOfficeService.deleteSysOffice(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除组织机构表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:office:delete")
    @ApiOperation(value = "批量删除SysOffice对象", notes = "批量删除组织机构表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysOffice(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysOfficeService.deleteSysOffices(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取组织机构表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:office:info")
    @ApiOperation(value = "获取SysOffice对象详情", notes = "查看组织机构表", response = SysOfficeQueryVo.class)
    public ApiResult<SysOfficeQueryVo> getSysOffice(@PathVariable("id") String id) throws Exception {
        SysOfficeQueryVo sysOfficeQueryVo = sysOfficeService.getSysOfficeById(id);
        return ApiResult.ok(sysOfficeQueryVo);
    }

    /**
     * 组织机构表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:office:page")
    @ApiOperation(value = "获取SysOffice分页列表", notes = "组织机构表分页列表", response = SysOfficeQueryVo.class)
    public ApiResult<Paging<SysOfficeQueryVo>> getSysOfficePageList(@Valid @RequestBody SysOfficeQueryParam sysOfficeQueryParam) throws Exception {
        Paging<SysOfficeQueryVo> paging = sysOfficeService.getSysOfficePageList(sysOfficeQueryParam);
        return ApiResult.ok(paging);
    }



    /**
     * 获取上级机构树
     */
    @PostMapping("/getParentCodeTree")
    @RequiresPermissions("sys:office:page")
    @ApiOperation(value = "获取SysOffice分页列表", notes = "组织机构表分页列表", response = SysOfficeQueryVo.class)
    public ApiResult<Paging<SysOfficeTreeQueryVo>> getParentCodeTree(@Valid @RequestBody SysOfficeQueryParam sysOfficeQueryParam) throws Exception {
        List<SysOfficeTreeQueryVo> list = sysOfficeService.getParentCodeTree(sysOfficeQueryParam);
        return ApiResult.ok(list);
    }

    /**
     * 获取机构用户树
     */
    @PostMapping("/getParentCodeUserTree")
    @RequiresPermissions("sys:office:page")
    @ApiOperation(value = "获取机构用户树", notes = "获取机构用户树", response = SysOfficeUserTreeQueryVo.class)
    public ApiResult<Paging<SysOfficeUserTreeQueryVo>> getParentCodeUserTree(@Valid @RequestBody SysOfficeQueryParam sysOfficeQueryParam) throws Exception {
        List<SysOfficeUserTreeQueryVo> list = sysOfficeService.getParentCodeUserTree(sysOfficeQueryParam);
        return ApiResult.ok(list);
    }
}

