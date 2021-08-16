package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.vo.CsMembercardOrderQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.util.UUIDUtil;
import com.io.yy.util.lang.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import com.io.yy.common.vo.Paging;
import com.io.yy.common.param.IdParam;

/**
 * <pre>
 * 会员卡购买记录 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Slf4j
@RestController
@RequestMapping("/csMembercardOrder")
@Api("会员卡购买记录 API")
public class CsMembercardOrderController extends BaseController {

    @Autowired
    private CsMembercardOrderService csMembercardOrderService;

    /**
     * 添加会员卡购买记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:membercard:order:add")
    @ApiOperation(value = "添加CsMembercardOrder对象", notes = "添加会员卡购买记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMembercardOrder(@Valid @RequestBody CsMembercardOrder csMembercardOrder) throws Exception {
        csMembercardOrder.setSourceType(0);
        csMembercardOrder.setPaymentStatus(2);
        csMembercardOrder.setOrderDate(new Date());
        csMembercardOrder.setStartTime(csMembercardOrder.getOrderDate());
        csMembercardOrder.setEndTime(DateUtils.plusMonth(csMembercardOrder.getStartTime(),csMembercardOrder.getValidPeriod()));
        csMembercardOrder.setOrderName(csMembercardOrder.getMembercardName()+'-'+
                DateUtils.getYYYYMMDDHHMMSS(csMembercardOrder.getOrderDate())+'-'+ UUIDUtil.getUUID());
        boolean flag = csMembercardOrderService.saveCsMembercardOrder(csMembercardOrder);
        if(!flag){
            return ApiResult.fail("用户已购买会员卡<"+csMembercardOrder.getMembercardId()+">");
        }
        return ApiResult.result(flag);
    }

    /**
     * 修改会员卡购买记录
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:membercard:order:update")
    @ApiOperation(value = "修改CsMembercardOrder对象", notes = "修改会员卡购买记录", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMembercardOrder(@Valid @RequestBody CsMembercardOrder csMembercardOrder) throws Exception {
        boolean flag = csMembercardOrderService.updateCsMembercardOrder(csMembercardOrder);
        return ApiResult.result(flag);
    }

    /**
     * 删除会员卡购买记录
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:membercard:order:delete")
    @ApiOperation(value = "删除CsMembercardOrder对象", notes = "删除会员卡购买记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMembercardOrder(@PathVariable("id") Long id) throws Exception {
        boolean flag = csMembercardOrderService.deleteCsMembercardOrder(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除会员卡购买记录
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:membercard:order:delete")
    @ApiOperation(value = "批量删除CsMembercardOrder对象", notes = "批量删除会员卡购买记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMembercardOrder(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csMembercardOrderService.deleteCsMembercardOrders(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取会员卡购买记录
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:membercard:order:info")
    @ApiOperation(value = "获取CsMembercardOrder对象详情", notes = "查看会员卡购买记录", response = CsMembercardOrderQueryVo.class)
    public ApiResult<CsMembercardOrderQueryVo> getCsMembercardOrder(@PathVariable("id") Long id) throws Exception {
        CsMembercardOrderQueryVo csMembercardOrderQueryVo = csMembercardOrderService.getCsMembercardOrderById(id);
        return ApiResult.ok(csMembercardOrderQueryVo);
    }

    /**
     * 会员卡购买记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:membercard:order:page")
    @ApiOperation(value = "获取CsMembercardOrder分页列表", notes = "会员卡购买记录分页列表", response = CsMembercardOrderQueryVo.class)
    public ApiResult<Paging<CsMembercardOrderQueryVo>> getCsMembercardOrderPageList(@Valid @RequestBody CsMembercardOrderQueryParam csMembercardOrderQueryParam) throws Exception {
        Paging<CsMembercardOrderQueryVo> paging = csMembercardOrderService.getCsMembercardOrderPageList(csMembercardOrderQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 会员卡购买记录修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:membercard:order:update")
    @ApiOperation(value = "修改CsMembercardOrder状态", notes = "会员卡购买记录修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsMembercardOrderQueryParam csMembercardOrderQueryParam) throws Exception {
        return ApiResult.ok(csMembercardOrderService.updateStatus(csMembercardOrderQueryParam));
    }

    /**
     * 根据获取当下购买的有效会员卡记录
     */
    @GetMapping("/getMemberCardForWx/{wxuserId}")
    @ApiOperation(value = "获取CsMembercardOrder对象详情", notes = "查看会员卡购买记录", response = CsMembercardOrderQueryVo.class)
    public ApiResult<CsMembercardOrderQueryVo> getMemberCardForWx(@PathVariable("wxuserId") Long wxuserId) throws Exception {
        CsMembercardOrderQueryVo csMembercardOrderQueryVo = csMembercardOrderService.getMemberCardForWx(wxuserId);
        return ApiResult.ok(csMembercardOrderQueryVo);
    }
}

