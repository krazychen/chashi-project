package com.io.yy.merchant.controller;

import com.io.yy.merchant.entity.CsMerchantNotify;
import com.io.yy.merchant.service.CsMerchantNotifyService;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.vo.CsMerchantNotifyQueryVo;
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
 * 商家通知人员记录 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-11-03
 */
@Slf4j
@RestController
@RequestMapping("/csMerchantNotify")
@Api("商家通知人员记录 API")
public class CsMerchantNotifyController extends BaseController {

    @Autowired
    private CsMerchantNotifyService csMerchantNotifyService;

    /**
     * 添加商家通知人员记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:merchant:notify:add")
    @ApiOperation(value = "添加CsMerchantNotify对象", notes = "添加商家通知人员记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchantNotify(@Valid @RequestBody CsMerchantNotify csMerchantNotify) throws Exception {
        boolean flag = csMerchantNotifyService.saveCsMerchantNotify(csMerchantNotify);
        return ApiResult.result(flag);
    }

    /**
     * 修改商家通知人员记录
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:merchant:notify:update")
    @ApiOperation(value = "修改CsMerchantNotify对象", notes = "修改商家通知人员记录", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMerchantNotify(@Valid @RequestBody CsMerchantNotify csMerchantNotify) throws Exception {
        boolean flag = csMerchantNotifyService.updateCsMerchantNotify(csMerchantNotify);
        return ApiResult.result(flag);
    }

    /**
     * 删除商家通知人员记录
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:merchant:notify:delete")
    @ApiOperation(value = "删除CsMerchantNotify对象", notes = "删除商家通知人员记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMerchantNotify(@PathVariable("id") Long id) throws Exception {
        boolean flag = csMerchantNotifyService.deleteCsMerchantNotify(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除商家通知人员记录
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:merchant:notify:delete")
    @ApiOperation(value = "批量删除CsMerchantNotify对象", notes = "批量删除商家通知人员记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMerchantNotify(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csMerchantNotifyService.deleteCsMerchantNotifys(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取商家通知人员记录
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:merchant:notify:info")
    @ApiOperation(value = "获取CsMerchantNotify对象详情", notes = "查看商家通知人员记录", response = CsMerchantNotifyQueryVo.class)
    public ApiResult<CsMerchantNotifyQueryVo> getCsMerchantNotify(@PathVariable("id") Long id) throws Exception {
        CsMerchantNotifyQueryVo csMerchantNotifyQueryVo = csMerchantNotifyService.getCsMerchantNotifyById(id);
        return ApiResult.ok(csMerchantNotifyQueryVo);
    }

    /**
     * 商家通知人员记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:merchant:notify:page")
    @ApiOperation(value = "获取CsMerchantNotify分页列表", notes = "商家通知人员记录分页列表", response = CsMerchantNotifyQueryVo.class)
    public ApiResult<Paging<CsMerchantNotifyQueryVo>> getCsMerchantNotifyPageList(@Valid @RequestBody CsMerchantNotifyQueryParam csMerchantNotifyQueryParam) throws Exception {
        Paging<CsMerchantNotifyQueryVo> paging = csMerchantNotifyService.getCsMerchantNotifyPageList(csMerchantNotifyQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 商家通知人员记录修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:merchant:notify:update")
    @ApiOperation(value = "修改CsMerchantNotify状态", notes = "商家通知人员记录修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsMerchantNotifyQueryParam csMerchantNotifyQueryParam) throws Exception {
        return ApiResult.ok(csMerchantNotifyService.updateStatus(csMerchantNotifyQueryParam));
    }


}

