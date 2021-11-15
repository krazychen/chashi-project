package com.io.yy.merchant.controller;

import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.service.CsRechargeConsumService;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private CsCouponReleasedService csCouponReleasedService;

    @Autowired
    private CsMembercardConsumService csMembercardConsumService;

    @Autowired
    private CsMembercardOrderService csMembercardOrderService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private CsRechargeConsumService csRechargeConsumService;
    /**
     * 添加商店茶室订单记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:merchant:order:add")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "添加CsMerchantOrder对象", notes = "添加商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchantOrder(@Valid @RequestBody CsMerchantOrder csMerchantOrder) throws Exception {
        return ApiResult.result(saveCsMerchantOrder(csMerchantOrder));
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
     * 添加商店茶室订单记录 - 余额
     */
    @PostMapping("/addCsMerchantOrderForWx")
    @ApiOperation(value = "添加CsMerchantOrder对象", notes = "添加商店茶室订单记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchantOrderForWx(@Valid @RequestBody CsMerchantOrder csMerchantOrder) throws Exception {
        return ApiResult.result(saveCsMerchantOrder(csMerchantOrder));
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

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception {
        // 保存茶室订单，订单保存成功后，即扣除优惠卷和会员优惠时长、金额，在支付失败、取消时，会重新删除优惠卷和优惠时长、金额的使用
        csMerchantOrder.setSourceType(0);
        csMerchantOrder.setPaymentStatus(2);
        Date now = new Date();
        LocalDate localDate=now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date newDate=java.sql.Date.valueOf(localDate);
        csMerchantOrder.setOrderDate(newDate);
        csMerchantOrder.setOrderName(csMerchantOrder.getRoomName()+'-'+
                DateUtils.getYYYYMMDDHHMMSS(csMerchantOrder.getOrderDate())+'-'+ UUIDUtil.getUUID());
        csMerchantOrder.setUsedStatus(0);
        boolean flag = csMerchantOrderService.saveCsMerchantOrder(csMerchantOrder);

        //如果有优惠卷，扣除优惠卷
        if(csMerchantOrder.getCouponReleasedId()!=null && csMerchantOrder.getCouponReleasedId()!=0){
            CsCouponReleasedQueryParam csCouponReleasedQueryParam = new CsCouponReleasedQueryParam();
            csCouponReleasedQueryParam.setId(csMerchantOrder.getCouponReleasedId());
            csCouponReleasedQueryParam.setIsUsed(1);
            csCouponReleasedQueryParam.setUsedTime(new Date());
            csCouponReleasedService.updateUsedStatus(csCouponReleasedQueryParam);
        }

        //如果是会员
        if(csMerchantOrder.getMembercardOrderId()!=null && csMerchantOrder.getMembercardOrderId()!=0){
            // 记录折扣金额
            CsMembercardConsum discountCsMembercardConsum = new CsMembercardConsum();
            discountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
            discountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
            //记录消费原始价格
            discountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
            discountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
            discountCsMembercardConsum.setConsumType(2);
            //优惠单价*原始总时长
            discountCsMembercardConsum.setConsumDiscountAmount(
                    DoubleUtils.subtract(csMerchantOrder.getOrderOriginPrice(),DoubleUtils.multiply(csMerchantOrder.getOrderUnitPrice(),csMerchantOrder.getOrderOriginTimenum().doubleValue())));
            discountCsMembercardConsum.setCousumDate(new Date());
            csMembercardConsumService.save(discountCsMembercardConsum);

            //如果使用优惠时长，扣除优惠时长
            if(csMerchantOrder.getOrderMbTimenum()!=null && csMerchantOrder.getOrderMbTimenum()!=0){
                CsMembercardConsum timeCsMembercardConsum = new CsMembercardConsum();
                timeCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
                timeCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
                //记录消费原始价格
                timeCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
                timeCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
                timeCsMembercardConsum.setConsumType(0);
                //优惠单价*原始总时长
                timeCsMembercardConsum.setConsumTime(csMerchantOrder.getOrderMbTimenum());
                timeCsMembercardConsum.setCousumDate(new Date());
                csMembercardConsumService.save(timeCsMembercardConsum);
            }else{
                csMerchantOrder.setOrderMbTimenum(0);
            }

            //如果使用优惠金额，扣除优惠金额
            if(csMerchantOrder.getOrderMbAmount()!=null && csMerchantOrder.getOrderMbAmount()!=0){
                // 记录折扣金额
                CsMembercardConsum amountCsMembercardConsum = new CsMembercardConsum();
                amountCsMembercardConsum.setCardOrderId(csMerchantOrder.getMembercardOrderId());
                amountCsMembercardConsum.setWxuserId(csMerchantOrder.getWxuserId());
                //记录消费原始价格
                amountCsMembercardConsum.setAmount(csMerchantOrder.getOrderOriginPrice());
                amountCsMembercardConsum.setRoomOrderId(csMerchantOrder.getId());
                amountCsMembercardConsum.setConsumType(1);
                //优惠单价*原始总时长
                amountCsMembercardConsum.setConsumAmount(csMerchantOrder.getOrderMbAmount());
                amountCsMembercardConsum.setCousumDate(new Date());
                csMembercardConsumService.save(amountCsMembercardConsum);
            }else{
                csMerchantOrder.setOrderMbAmount(new Double(0));
            }

            //更新会员卡的剩余时长和剩余金额
            if(csMerchantOrder.getOrderMbAmount()!=0 || csMerchantOrder.getOrderMbTimenum()!=0) {
                CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
                csMembercardOrderQueryParam.setId(csMerchantOrder.getMembercardOrderId());
                csMembercardOrderQueryParam.setRestDiscountPrice(csMerchantOrder.getOrderMbAmount());
                csMembercardOrderQueryParam.setRestDiscountTime(csMerchantOrder.getOrderMbTimenum().doubleValue());
                csMembercardOrderService.reduceRest(csMembercardOrderQueryParam);
            }
        }

        //如果是余额支付，则需要更新账户余额信息
        if(csMerchantOrder.getPaymentType().equals(1)){
            WxUserQueryParam wxUserQueryParam = new WxUserQueryParam();
            wxUserQueryParam.setId(csMerchantOrder.getWxuserId());
            wxUserQueryParam.setBalance(csMerchantOrder.getOrderPrice());
            wxUserService.reduceBalance(wxUserQueryParam);

            CsRechargeConsum csRechargeConsum = new CsRechargeConsum();
            csRechargeConsum.setWxuserId(csMerchantOrder.getWxuserId());
            csRechargeConsum.setCousumAmount(csMerchantOrder.getOrderPrice());
            csRechargeConsum.setCousumDate(new Date());
            csRechargeConsum.setRoomOrderId(csMerchantOrder.getId());
            csRechargeConsum.setConsumType(0);
            csRechargeConsumService.saveCsRechargeConsum(csRechargeConsum);
        }

        return flag;
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
    @GetMapping("/openLock")
    @ApiOperation(value = "一键开锁", notes = "一键开锁", response = CsMerchantOrderQueryVo.class)
    public ApiResult<String> openLock(@Valid @RequestBody CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        return ApiResult.ok(csMerchantOrderService.openLock(csMerchantOrderQueryParam));
    }
}

