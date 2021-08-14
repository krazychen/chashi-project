package com.io.yy.merchant.controller;

import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
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
 * 商店茶室订单记录 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Slf4j
@RestController
@RequestMapping("/csMerchantOrder")
@Api("商店茶室订单记录 API")
public class CsMerchantOrderController extends BaseController {

    @Autowired
    private CsMerchantOrderService csMerchantOrderService;

    /**
     * 添加商店茶室订单记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:merchant:order:add")
    @ApiOperation(value = "添加CsMerchantOrder对象", notes = "添加商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchantOrder(@Valid @RequestBody CsMerchantOrder csMerchantOrder) throws Exception {
        boolean flag = csMerchantOrderService.saveCsMerchantOrder(csMerchantOrder);
        return ApiResult.result(flag);
    }

    /**
     * 修改商店茶室订单记录
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:merchant:order:update")
    @ApiOperation(value = "修改CsMerchantOrder对象", notes = "修改商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMerchantOrder(@Valid @RequestBody CsMerchantOrder csMerchantOrder) throws Exception {
        boolean flag = csMerchantOrderService.updateCsMerchantOrder(csMerchantOrder);
        return ApiResult.result(flag);
    }

    /**
     * 删除商店茶室订单记录
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:merchant:order:delete")
    @ApiOperation(value = "删除CsMerchantOrder对象", notes = "删除商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMerchantOrder(@PathVariable("id") Long id) throws Exception {
        boolean flag = csMerchantOrderService.deleteCsMerchantOrder(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除商店茶室订单记录
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:merchant:order:delete")
    @ApiOperation(value = "批量删除CsMerchantOrder对象", notes = "批量删除商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMerchantOrder(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csMerchantOrderService.deleteCsMerchantOrders(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取商店茶室订单记录
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:merchant:order:info")
    @ApiOperation(value = "获取CsMerchantOrder对象详情", notes = "查看商店茶室订单记录", response = CsMerchantOrderQueryVo.class)
    public ApiResult<CsMerchantOrderQueryVo> getCsMerchantOrder(@PathVariable("id") Long id) throws Exception {
        CsMerchantOrderQueryVo csMerchantOrderQueryVo = csMerchantOrderService.getCsMerchantOrderById(id);
        return ApiResult.ok(csMerchantOrderQueryVo);
    }

    /**
     * 商店茶室订单记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:merchant:order:page")
    @ApiOperation(value = "获取CsMerchantOrder分页列表", notes = "商店茶室订单记录分页列表", response = CsMerchantOrderQueryVo.class)
    public ApiResult<Paging<CsMerchantOrderQueryVo>> getCsMerchantOrderPageList(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        Paging<CsMerchantOrderQueryVo> paging = csMerchantOrderService.getCsMerchantOrderPageList(csMerchantOrderQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 商店茶室订单记录修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:merchant:order:update")
    @ApiOperation(value = "修改CsMerchantOrder状态", notes = "商店茶室订单记录修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.updateStatus(csMerchantOrderQueryParam));
    }

    /**
     * 根据tearoomid和预订日期获取当前茶室已经被预定的时间段，返回是时间段的一个包含","的字符串
     */
    @PostMapping("/getTimeRangeForWx")
    @ApiOperation(value = "获取当前茶室已经被预定的时间段，返回是时间段的一个包含\",\"的字符串", notes = "获取当前茶室已经被预定的时间段", response = ApiResult.class)
    public ApiResult<String> getTimeRangeForWx(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.getTimeRangeForWx(csMerchantOrderQueryParam));
    }
}

