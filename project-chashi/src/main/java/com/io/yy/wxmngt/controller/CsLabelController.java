package com.io.yy.wxmngt.controller;

import com.io.yy.wxmngt.entity.CsLabel;
import com.io.yy.wxmngt.service.CsLabelService;
import com.io.yy.wxmngt.param.CsLabelQueryParam;
import com.io.yy.wxmngt.vo.CsLabelQueryVo;
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
 * 标签管理 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-07-08
 */
@Slf4j
@RestController
@RequestMapping("/csLabel")
@Api("标签管理 API")
public class CsLabelController extends BaseController {

    @Autowired
    private CsLabelService csLabelService;

    /**
     * 添加标签管理
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:label:add")
    @ApiOperation(value = "添加CsLabel对象", notes = "添加标签管理", response = ApiResult.class)
    public ApiResult<Boolean> addCsLabel(@Valid @RequestBody CsLabel csLabel) throws Exception {
        boolean flag = csLabelService.saveCsLabel(csLabel);
        return ApiResult.result(flag);
    }

    /**
     * 修改标签管理
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:label:update")
    @ApiOperation(value = "修改CsLabel对象", notes = "修改标签管理", response = ApiResult.class)
    public ApiResult<Boolean> updateCsLabel(@Valid @RequestBody CsLabel csLabel) throws Exception {
        boolean flag = csLabelService.updateCsLabel(csLabel);
        return ApiResult.result(flag);
    }

    /**
     * 删除标签管理
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:label:delete")
    @ApiOperation(value = "删除CsLabel对象", notes = "删除标签管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsLabel(@PathVariable("id") Long id) throws Exception {
        boolean flag = csLabelService.deleteCsLabel(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除标签管理
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:label:delete")
    @ApiOperation(value = "批量删除CsLabel对象", notes = "批量删除标签管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsLabel(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csLabelService.deleteCsLabels(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取标签管理
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:label:info")
    @ApiOperation(value = "获取CsLabel对象详情", notes = "查看标签管理", response = CsLabelQueryVo.class)
    public ApiResult<CsLabelQueryVo> getCsLabel(@PathVariable("id") Long id) throws Exception {
        CsLabelQueryVo csLabelQueryVo = csLabelService.getCsLabelById(id);
        return ApiResult.ok(csLabelQueryVo);
    }

    /**
     * 标签管理分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:label:page")
    @ApiOperation(value = "获取CsLabel分页列表", notes = "标签管理分页列表", response = CsLabelQueryVo.class)
    public ApiResult<Paging<CsLabelQueryVo>> getCsLabelPageList(@Valid @RequestBody CsLabelQueryParam csLabelQueryParam) throws Exception {
        Paging<CsLabelQueryVo> paging = csLabelService.getCsLabelPageList(csLabelQueryParam);
        return ApiResult.ok(paging);
    }

}

