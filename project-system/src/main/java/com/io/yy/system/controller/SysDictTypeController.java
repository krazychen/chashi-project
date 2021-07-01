package com.io.yy.system.controller;

import com.io.yy.system.entity.SysDictType;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.service.SysDictTypeService;
import com.io.yy.system.param.SysDictTypeQueryParam;
import com.io.yy.system.vo.SysDictTypeQueryVo;
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
 * 字典类型表 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Slf4j
@RestController
@RequestMapping("/sysDictType")
@Api("字典类型表 API")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 添加字典类型表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:dict:type:add")
    @ApiOperation(value = "添加SysDictType对象", notes = "添加字典类型表", response = ApiResult.class)
    public ApiResult<Boolean> addSysDictType(@Valid @RequestBody SysDictType sysDictType) throws Exception {
        boolean flag = sysDictTypeService.saveSysDictType(sysDictType);
        return ApiResult.result(flag);
    }

    /**
     * 修改字典类型表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:dict:type:update")
    @ApiOperation(value = "修改SysDictType对象", notes = "修改字典类型表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysDictType(@Valid @RequestBody SysDictType sysDictType) throws Exception {
        boolean flag = sysDictTypeService.updateSysDictType(sysDictType);
        return ApiResult.result(flag);
    }

    /**
     * 删除字典类型表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:dict:type:delete")
    @ApiOperation(value = "删除SysDictType对象", notes = "删除字典类型表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysDictType(@PathVariable("id") String id) throws Exception {
        boolean flag = sysDictTypeService.deleteSysDictType(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除字典类型表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:dict:type:delete")
    @ApiOperation(value = "批量删除SysDictType对象", notes = "批量删除字典类型表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysDictType(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysDictTypeService.deleteSysDictTypes(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取字典类型表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dict:type:info")
    @ApiOperation(value = "获取SysDictType对象详情", notes = "查看字典类型表", response = SysDictTypeQueryVo.class)
    public ApiResult<SysDictTypeQueryVo> getSysDictType(@PathVariable("id") String id) throws Exception {
        SysDictTypeQueryVo sysDictTypeQueryVo = sysDictTypeService.getSysDictTypeById(id);
        return ApiResult.ok(sysDictTypeQueryVo);
    }

    /**
     * 字典类型表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:dict:type:page")
    @ApiOperation(value = "获取SysDictType分页列表", notes = "字典类型表分页列表", response = SysDictTypeQueryVo.class)
    public ApiResult<Paging<SysDictTypeQueryVo>> getSysDictTypePageList(@Valid @RequestBody SysDictTypeQueryParam sysDictTypeQueryParam) throws Exception {
        Paging<SysDictTypeQueryVo> paging = sysDictTypeService.getSysDictTypePageList(sysDictTypeQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 修改status状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("sys:dict:type:status")
    @ApiOperation(value = "修改status状态", notes = "修改status状态", response = Boolean.class)
    public ApiResult<Boolean> updateStatusById(@Valid @RequestBody SysDictTypeStatusQueryParam  sysDictTypeStatusQueryParam){
        return ApiResult.ok(sysDictTypeService.updateStatusById(sysDictTypeStatusQueryParam));
    }

}

