package com.io.yy.system.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysArea;
import com.io.yy.system.param.SysAreaQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.service.SysAreaService;
import com.io.yy.system.vo.SysAreaLazyQueryVo;
import com.io.yy.system.vo.SysAreaQueryVo;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 行政区划 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-04
 */
@Slf4j
@RestController
@RequestMapping("/sysArea")
@Api("行政区划 API")
public class SysAreaController extends BaseController {

    @Autowired
    private SysAreaService sysAreaService;

    /**
     * 添加行政区划
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:area:add")
    @ApiOperation(value = "添加SysArea对象", notes = "添加行政区划", response = ApiResult.class)
    public ApiResult<Boolean> addSysArea(@Valid @RequestBody SysArea sysArea) throws Exception {
        boolean flag = sysAreaService.saveSysArea(sysArea);
        return ApiResult.result(flag);
    }

    /**
     * 修改行政区划
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:area:update")
    @ApiOperation(value = "修改SysArea对象", notes = "修改行政区划", response = ApiResult.class)
    public ApiResult<Boolean> updateSysArea(@Valid @RequestBody SysArea sysArea) throws Exception {
        boolean flag = sysAreaService.updateSysArea(sysArea);
        return ApiResult.result(flag);
    }

    /**
     * 删除行政区划
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:area:delete")
    @ApiOperation(value = "删除SysArea对象", notes = "删除行政区划", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysArea(@PathVariable("id") String id) throws Exception {
        boolean flag = sysAreaService.deleteSysArea(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除行政区划
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:area:delete")
    @ApiOperation(value = "批量删除SysArea对象", notes = "批量删除行政区划", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysArea(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysAreaService.deleteSysAreas(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取行政区划
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:area:info")
    @ApiOperation(value = "获取SysArea对象详情", notes = "查看行政区划", response = SysAreaQueryVo.class)
    public ApiResult<SysAreaQueryVo> getSysArea(@PathVariable("id") String id) throws Exception {
        SysAreaQueryVo sysAreaQueryVo = sysAreaService.getSysAreaById(id);
        return ApiResult.ok(sysAreaQueryVo);
    }

    /**
     * 行政区划分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:area:page")
    @ApiOperation(value = "获取SysArea分页列表", notes = "行政区划分页列表", response = SysAreaQueryVo.class)
    public ApiResult<Paging<SysAreaQueryVo>> getSysAreaPageList(@Valid @RequestBody SysAreaQueryParam sysAreaQueryParam) throws Exception {
        Paging<SysAreaQueryVo> paging = sysAreaService.getSysAreaPageList(sysAreaQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 行政区划分页列表
     */
    @PostMapping("/getSysAreaPageListLazy")
    @RequiresPermissions("sys:area:page")
    @ApiOperation(value = "获取SysArea分页列表", notes = "行政区划分页列表", response = SysAreaQueryVo.class)
    public ApiResult<Paging<SysAreaLazyQueryVo>> getSysAreaPageListLazy(@Valid @RequestBody SysAreaQueryParam sysAreaQueryParam) throws Exception {
        Paging<SysAreaLazyQueryVo> paging = sysAreaService.getSysAreaPageListLazy(sysAreaQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 精简的行政区域树结构
     */
    @PostMapping("/getSysAreaSimplifyPageList")
    @RequiresPermissions("sys:area:simplify:list")
    @ApiOperation(value = "精简的行政区域树结构", notes = "精简的行政区域树结构", response = SysMenuQueryVo.class)
    public ApiResult<List<SysMenuTreeQueryVo>> getSysAreaSimplifyPageList(@Valid @RequestBody SysAreaQueryParam sysAreaQueryParam) throws Exception {
        List<SysMenuTreeQueryVo> list = sysAreaService.getSysAreaSimplifyPageList(sysAreaQueryParam);
        return ApiResult.ok(list);
    }

    /**
     * 精简的行政区域树结构
     */
    @PostMapping("/getSysAreaSimplifyList")
    @RequiresPermissions("sys:area:simplify:list")
    @ApiOperation(value = "精简的行政区域树结构", notes = "精简的行政区域树结构", response = SysMenuQueryVo.class)
    public ApiResult<List<SysMenuTreeQueryVo>> getSysAreaRedisSimplifyPageList() throws Exception {
        List<SysMenuTreeQueryVo> list = sysAreaService.getSysAreaRidesSimplifyPageList();
        return ApiResult.ok(list);
    }

    /**
     * 修改status状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("sys:area:status")
    @ApiOperation(value = "修改status状态", notes = "修改status状态", response = Boolean.class)
    public ApiResult<Boolean> updateStatusById(@Valid @RequestBody SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam){
        return ApiResult.ok(sysAreaService.updateStatusById(sysDictTypeStatusQueryParam));
    }

    /**
     * 导入专业
     *
     * @param file
     * @return
            */
    @PostMapping("/import")
    @RequiresPermissions("sys:area:import")
    @ApiOperation(value = "导入省市区", notes = "导入省市区")
    public ApiResult<Boolean> importData(@RequestParam("file") MultipartFile file) {
        try {
            return ApiResult.ok(sysAreaService.importData(file));
        } catch (Exception e) {
            return ApiResult.ok(false, e.getMessage());
        }
    }


    /**
     * 根据类型 跟父节点 查询直属下级
     */
    @GetMapping("/getAreaByAreaType")
    @ApiOperation(value = "根据类型 跟父节点 查询直属下级", notes = "根据类型 跟父节点 查询直属下级", response = SysAreaQueryVo.class)
    public ApiResult<List<SysAreaQueryVo>> getAreaByAreaType(@RequestParam("areaType") String areaType,@RequestParam("parentCode") String parentCode ) throws Exception {
        List<SysAreaQueryVo> list = sysAreaService.getAreaByAreaType(areaType,parentCode);
        return ApiResult.ok(list);
    }

    /**
     * 根据类型 跟父节点 查询直属下级
     */
    @GetMapping("/getReleaseCity")
    @ApiOperation(value = "获取发布状态的城市列表", notes = "获取发布状态的城市列表", response = SysAreaQueryVo.class)
    public ApiResult<List<SysAreaQueryVo>> getReleaseCity() throws Exception {
        List<SysAreaQueryVo> list = sysAreaService.getReleaseCity();
        return ApiResult.ok(list);
    }

    /**
     * 根据类型 跟父节点 查询直属下级
     */
    @GetMapping("/getReleaseCityWx")
    @ApiOperation(value = "获取发布状态的城市列表", notes = "获取发布状态的城市列表", response = SysAreaQueryVo.class)
    public ApiResult<List<SysAreaQueryVo>> getReleaseCityForWx() throws Exception {
        List<SysAreaQueryVo> list = sysAreaService.getReleaseCity();
        return ApiResult.ok(list);
    }
}

