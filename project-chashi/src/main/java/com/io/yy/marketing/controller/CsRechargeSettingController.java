package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsRechargeSetting;
import com.io.yy.marketing.service.CsRechargeSettingService;
import com.io.yy.marketing.param.CsRechargeSettingQueryParam;
import com.io.yy.marketing.vo.CsRechargeSettingQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.wxops.service.WxUserService;
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
 * 充值设置 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Slf4j
@RestController
@RequestMapping("/csRechargeSetting")
@Api("充值设置 API")
public class CsRechargeSettingController extends BaseController {

    @Autowired
    private CsRechargeSettingService csRechargeSettingService;

    /**
     * 添加充值设置
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:recharge:setting:add")
    @ApiOperation(value = "添加CsRechargeSetting对象", notes = "添加充值设置", response = ApiResult.class)
    public ApiResult<Boolean> addCsRechargeSetting(@Valid @RequestBody CsRechargeSetting csRechargeSetting) throws Exception {
        boolean flag = csRechargeSettingService.saveCsRechargeSetting(csRechargeSetting);
        return ApiResult.result(flag);
    }

    /**
     * 修改充值设置
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:recharge:setting:update")
    @ApiOperation(value = "修改CsRechargeSetting对象", notes = "修改充值设置", response = ApiResult.class)
    public ApiResult<Boolean> updateCsRechargeSetting(@Valid @RequestBody CsRechargeSetting csRechargeSetting) throws Exception {
        boolean flag = csRechargeSettingService.updateCsRechargeSetting(csRechargeSetting);
        return ApiResult.result(flag);
    }

    /**
     * 删除充值设置
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:recharge:setting:delete")
    @ApiOperation(value = "删除CsRechargeSetting对象", notes = "删除充值设置", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsRechargeSetting(@PathVariable("id") Long id) throws Exception {
        boolean flag = csRechargeSettingService.deleteCsRechargeSetting(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除充值设置
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:recharge:setting:delete")
    @ApiOperation(value = "批量删除CsRechargeSetting对象", notes = "批量删除充值设置", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsRechargeSetting(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csRechargeSettingService.deleteCsRechargeSettings(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取充值设置
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:recharge:setting:info")
    @ApiOperation(value = "获取CsRechargeSetting对象详情", notes = "查看充值设置", response = CsRechargeSettingQueryVo.class)
    public ApiResult<CsRechargeSettingQueryVo> getCsRechargeSetting(@PathVariable("id") Long id) throws Exception {
        CsRechargeSettingQueryVo csRechargeSettingQueryVo = csRechargeSettingService.getCsRechargeSettingById(id);
        return ApiResult.ok(csRechargeSettingQueryVo);
    }

    /**
     * 充值设置分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:recharge:setting:page")
    @ApiOperation(value = "获取CsRechargeSetting分页列表", notes = "充值设置分页列表", response = CsRechargeSettingQueryVo.class)
    public ApiResult<Paging<CsRechargeSettingQueryVo>> getCsRechargeSettingPageList(@Valid @RequestBody CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception {
        Paging<CsRechargeSettingQueryVo> paging = csRechargeSettingService.getCsRechargeSettingPageList(csRechargeSettingQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 充值设置修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:recharge:setting:update")
    @ApiOperation(value = "修改CsRechargeSetting状态", notes = "充值设置修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception {
        return ApiResult.ok(csRechargeSettingService.updateStatus(csRechargeSettingQueryParam));
    }

    /**
     * 获取启用的充值设置列表forwx
     */
    @PostMapping("/getSettingListForWx")
    @ApiOperation(value = "获取启用的充值设置列表forwx", notes = "获取启用的充值设置列表forwx", response = List.class)
    public ApiResult<List<CsRechargeSettingQueryVo>> getSettingListForWx(@Valid @RequestBody CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception {
        csRechargeSettingQueryParam.setStatus("1");
        return ApiResult.ok(csRechargeSettingService.getSettingListForWx(csRechargeSettingQueryParam));
    }
}

