package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsCoupon;
import com.io.yy.marketing.service.CsCouponService;
import com.io.yy.marketing.param.CsCouponQueryParam;
import com.io.yy.marketing.vo.CsCouponQueryVo;
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
 * 优惠卷 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
@Slf4j
@RestController
@RequestMapping("/csCoupon")
@Api("优惠卷 API")
public class CsCouponController extends BaseController {

    @Autowired
    private CsCouponService csCouponService;

    /**
     * 添加优惠卷
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:coupon:add")
    @ApiOperation(value = "添加CsCoupon对象", notes = "添加优惠卷", response = ApiResult.class)
    public ApiResult<Boolean> addCsCoupon(@Valid @RequestBody CsCoupon csCoupon) throws Exception {
        boolean flag = csCouponService.saveCsCoupon(csCoupon);
        return ApiResult.result(flag);
    }

    /**
     * 修改优惠卷
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:coupon:update")
    @ApiOperation(value = "修改CsCoupon对象", notes = "修改优惠卷", response = ApiResult.class)
    public ApiResult<Boolean> updateCsCoupon(@Valid @RequestBody CsCoupon csCoupon) throws Exception {
        boolean flag = csCouponService.updateCsCoupon(csCoupon);
        return ApiResult.result(flag);
    }

    /**
     * 删除优惠卷
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:coupon:delete")
    @ApiOperation(value = "删除CsCoupon对象", notes = "删除优惠卷", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsCoupon(@PathVariable("id") Long id) throws Exception {
        boolean flag = csCouponService.deleteCsCoupon(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除优惠卷
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:coupon:delete")
    @ApiOperation(value = "批量删除CsCoupon对象", notes = "批量删除优惠卷", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsCoupon(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csCouponService.deleteCsCoupons(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取优惠卷
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:coupon:info")
    @ApiOperation(value = "获取CsCoupon对象详情", notes = "查看优惠卷", response = CsCouponQueryVo.class)
    public ApiResult<CsCouponQueryVo> getCsCoupon(@PathVariable("id") Long id) throws Exception {
        CsCouponQueryVo csCouponQueryVo = csCouponService.getCsCouponById(id);
        return ApiResult.ok(csCouponQueryVo);
    }

    /**
     * 优惠卷分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:coupon:page")
    @ApiOperation(value = "获取CsCoupon分页列表", notes = "优惠卷分页列表", response = CsCouponQueryVo.class)
    public ApiResult<Paging<CsCouponQueryVo>> getCsCouponPageList(@Valid @RequestBody CsCouponQueryParam csCouponQueryParam) throws Exception {
        Paging<CsCouponQueryVo> paging = csCouponService.getCsCouponPageList(csCouponQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 优惠卷修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:coupon:update")
    @ApiOperation(value = "修改CsCoupon状态", notes = "优惠卷修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsCouponQueryParam csCouponQueryParam) throws Exception {
        return ApiResult.ok(csCouponService.updateStatus(csCouponQueryParam));
    }


}

