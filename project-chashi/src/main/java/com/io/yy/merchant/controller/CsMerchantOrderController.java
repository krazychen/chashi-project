package com.io.yy.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.service.CsRechargeConsumService;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.param.CsTearoomQueryParam;
import com.io.yy.merchant.service.CsMerchantNotifyService;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.CsMerchantNotifyQueryVo;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.util.UUIDUtil;
import com.io.yy.util.lang.DateUtils;
import com.io.yy.util.lang.DoubleUtils;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.io.yy.common.vo.Paging;

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
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "添加CsMerchantOrder对象", notes = "添加商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchantOrder(@Valid @RequestBody CsMerchantOrder csMerchantOrder) throws Exception {
        return ApiResult.result(csMerchantOrderService.saveCsMerchantOrderForWX(csMerchantOrder));
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
    @PostMapping("/getTimeRange")
    @ApiOperation(value = "获取当前茶室已经被预定的时间段，返回是时间段的一个包含\",\"的字符串", notes = "获取当前茶室已经被预定的时间段", response = ApiResult.class)
    public ApiResult<String> getTimeRange(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.getTimeRangeForWx(csMerchantOrderQueryParam));
    }

    /**
     * 根据tearoomid和预订日期获取当前茶室已经被预定的时间段，返回是时间段的一个包含","的字符串
     */
    @PostMapping("/getTimeRangeForWx")
    @ApiOperation(value = "获取当前茶室已经被预定的时间段，返回是时间段的一个包含\",\"的字符串", notes = "获取当前茶室已经被预定的时间段", response = ApiResult.class)
    public ApiResult<String> getTimeRangeForWx(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.getTimeRangeForWx(csMerchantOrderQueryParam));
    }

    /**
     * 根据tearoomid和预订日期获取当前茶室含保洁时间已经被预定的时间段，返回是时间段的一个包含","的字符串
     */
    @PostMapping("/getTimeRangeCleanForWx")
    @ApiOperation(value = "获取当前茶室含保洁时间已经被预定的时间段，返回是时间段的一个包含\",\"的字符串", notes = "获取当前茶室已经被预定的时间段", response = ApiResult.class)
    public ApiResult<String> getTimeRangeCleanForWx(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.getTimeRangeCleanForWx(csMerchantOrderQueryParam));
    }

    /**
     * 根据tearoomid和当前时间判断是否存在订单，有订单返回订单id，没有订单返回null
     */
    @PostMapping("/getOrderByCurrent")
    @ApiOperation(value = " 根据tearoomid和当前时间判断是否存在订单，有订单返回订单id，没有订单返回null", notes = "判断当前时间是否有订单", response = ApiResult.class)
    public ApiResult<Long> getOrderByCurrent(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.getOrderByCurrent(csMerchantOrderQueryParam));
    }

    /**
     * 添加商店茶室订单记录 - 余额
     */
    @PostMapping("/addCsMerchantOrderForWx")
    @ApiOperation(value = "添加CsMerchantOrder对象", notes = "添加商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchantOrderForWx(@Valid @RequestBody CsMerchantOrder csMerchantOrder) throws Exception {
        return ApiResult.result(csMerchantOrderService.saveCsMerchantOrderForWX(csMerchantOrder));
    }

    /**
     * 商店茶室订单记录分页列表for Wx
     */
    @PostMapping("/getCsMerchantOrderListForWx")
    @ApiOperation(value = "获取CsMerchantOrder列表", notes = "商店茶室订单记录列表", response = CsMerchantOrderQueryVo.class)
    public ApiResult<Paging<CsMerchantOrderQueryVo>> getCsMerchantOrderListForWx(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        Paging<CsMerchantOrderQueryVo> paging = csMerchantOrderService.getCsMerchantOrderPageList(csMerchantOrderQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 获取商店茶室订单记录forWx
     */
    @GetMapping("/infoForWx/{id}")
    @ApiOperation(value = "获取wxCsMerchantOrder对象详情", notes = "查看商店茶室订单记录", response = CsMerchantOrderQueryVo.class)
    public ApiResult<CsMerchantOrderQueryVo> getCsMerchantOrderForWx(@PathVariable("id") String id) throws Exception {
        CsMerchantOrderQueryVo csMerchantOrderQueryVo = csMerchantOrderService.getCsMerchantOrderById(id);
        return ApiResult.ok(csMerchantOrderQueryVo);
    }

    /**
     * 获取商店茶室订单的开锁密码记录forWx，需要传入茶室订单id
     */
    @GetMapping("/lockKeyForWx/{id}")
    @ApiOperation(value = "获取锁密码", notes = "获取锁密码", response = CsMerchantOrderQueryVo.class)
    public ApiResult<CsMerchantOrderQueryVo> lockKeyForWx(@RequestBody@PathVariable("id") String id) throws Exception {
        CsMerchantOrderQueryParam csMerchantOrderQueryParam = new CsMerchantOrderQueryParam();
        csMerchantOrderQueryParam.setId(Long.valueOf(id));
        return ApiResult.ok(csMerchantOrderService.getLockKey(csMerchantOrderQueryParam));
    }

    /**
     * 一键开锁，需要传入茶室订单id对应的商店id和茶室id
     */
    @PostMapping("/openLock")
    @ApiOperation(value = "一键开锁", notes = "一键开锁", response = CsMerchantOrderQueryVo.class)
    public ApiResult<String> openLock(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.openLock(csMerchantOrderQueryParam));
    }

    /**
     * 一键开房门锁，需要传入茶室订单id对应的商店id和茶室id
     */
    @PostMapping("/openRoomLock")
    @ApiOperation(value = "一键开锁", notes = "一键开锁", response = CsMerchantOrderQueryVo.class)
    public ApiResult<String> openRoomLock(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.openRoomLock(csMerchantOrderQueryParam));
    }

    /**
     * 导出功能
     */
    @PostMapping("/exportList")
    @RequiresPermissions("cs:merchant:order:page")
    @ApiOperation(value = "导出", notes = "导出")
    public void exportStudentList(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        csMerchantOrderService.exportList(csMerchantOrderQueryParam);
    }
}

