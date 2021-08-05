package com.io.yy.wxmngt.controller;

import com.io.yy.wxmngt.entity.CsFacilities;
import com.io.yy.wxmngt.service.CsFacilitiesService;
import com.io.yy.wxmngt.param.CsFacilitiesQueryParam;
import com.io.yy.wxmngt.vo.CsFacilitiesQueryVo;
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
 * 服务设施管理 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Slf4j
@RestController
@RequestMapping("/csFacilities")
@Api("服务设施管理 API")
public class CsFacilitiesController extends BaseController {
    
    @Autowired
    private CsFacilitiesService csFacilitiesService;

    /**
     * 添加服务设施管理
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:facilities:add")
    @ApiOperation(value = "添加CsFacilities对象", notes = "添加服务设施管理", response = ApiResult.class)
    public ApiResult<Boolean> addCsFacilities(@Valid @RequestBody CsFacilities csFacilities) throws Exception {
            boolean flag = csFacilitiesService.saveCsFacilities(csFacilities);
            return ApiResult.result(flag);
    }

    /**
     * 修改服务设施管理
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:facilities:update")
    @ApiOperation(value = "修改CsFacilities对象", notes = "修改服务设施管理", response = ApiResult.class)
    public ApiResult<Boolean> updateCsFacilities(@Valid @RequestBody CsFacilities csFacilities) throws Exception {
            boolean flag = csFacilitiesService.updateCsFacilities(csFacilities);
            return ApiResult.result(flag);
    }

    /**
     * 删除服务设施管理
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:facilities:delete")
    @ApiOperation(value = "删除CsFacilities对象", notes = "删除服务设施管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsFacilities(@PathVariable("id") Long id) throws Exception {
            boolean flag = csFacilitiesService.deleteCsFacilities(id);
            return ApiResult.result(flag);
    }

    /**
     * 批量删除服务设施管理
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:facilities:delete")
    @ApiOperation(value = "批量删除CsFacilities对象", notes = "批量删除服务设施管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsFacilities(@Valid @RequestBody List<String> idList) throws Exception {
            boolean flag = csFacilitiesService.deleteCsFacilitiess(idList);
            return ApiResult.result(flag);
    }

    /**
     * 获取服务设施管理
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:facilities:info")
    @ApiOperation(value = "获取CsFacilities对象详情", notes = "查看服务设施管理", response = CsFacilitiesQueryVo.class)
    public ApiResult<CsFacilitiesQueryVo> getCsFacilities(@PathVariable("id") Long id) throws Exception {
        CsFacilitiesQueryVo csFacilitiesQueryVo = csFacilitiesService.getCsFacilitiesById(id);
        return ApiResult.ok(csFacilitiesQueryVo);
    }

    /**
     * 服务设施管理分页列表
     */
    @PostMapping("/getPageList")
        @RequiresPermissions("cs:facilities:page")
    @ApiOperation(value = "获取CsFacilities分页列表", notes = "服务设施管理分页列表", response = CsFacilitiesQueryVo.class)
    public ApiResult<Paging<CsFacilitiesQueryVo>> getCsFacilitiesPageList(@Valid @RequestBody CsFacilitiesQueryParam csFacilitiesQueryParam) throws Exception {
        Paging<CsFacilitiesQueryVo> paging = csFacilitiesService.getCsFacilitiesPageList(csFacilitiesQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 服务设施管理修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:facilities:update")
    @ApiOperation(value = "修改CsFacilities状态", notes = "服务设施管理修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsFacilitiesQueryParam csFacilitiesQueryParam) throws Exception {
        return ApiResult.ok(csFacilitiesService.updateStatus(csFacilitiesQueryParam));
    }


}

