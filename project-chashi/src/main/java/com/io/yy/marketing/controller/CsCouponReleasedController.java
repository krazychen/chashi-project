package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsCouponReleased;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.vo.CsCouponReleasedQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

import com.io.yy.common.vo.Paging;
import com.io.yy.common.param.IdParam;

/**
 * <pre>
 * 优惠卷发放/领取记录 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
@Slf4j
@RestController
@RequestMapping("/csCouponReleased")
@Api("优惠卷发放/领取记录 API")
public class CsCouponReleasedController extends BaseController {

    @Autowired
    private CsCouponReleasedService csCouponReleasedService;

    /**
     * 添加优惠卷发放/领取记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:coupon:released:add")
    @ApiOperation(value = "添加CsCouponReleased对象", notes = "添加优惠卷发放/领取记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsCouponReleased(@Valid @RequestBody CsCouponReleased csCouponReleased) throws Exception {
        boolean flag = csCouponReleasedService.saveCsCouponReleased(csCouponReleased);
        return ApiResult.result(flag);
    }

    /**
     * 批量添加优惠卷发放/领取记录
     */
    @PostMapping("/batchedAdd")
    @RequiresPermissions("cs:coupon:released:add")
    @ApiOperation(value = "添加CsCouponReleased对象", notes = "添加优惠卷发放/领取记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsCouponReleasedBatched(@Valid @RequestBody CsCouponReleased csCouponReleased) throws Exception {
        boolean flag = true;
        for (int i=0; i<csCouponReleased.getReleasedNum(); i++){
            CsCouponReleased csCouponReleasedNew = new CsCouponReleased();
            BeanUtils.copyProperties(csCouponReleased,csCouponReleasedNew);
            flag = csCouponReleasedService.saveCsCouponReleased(csCouponReleasedNew);
        }
        return ApiResult.result(flag);
    }

    /**
     * 修改优惠卷发放/领取记录
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:coupon:released:update")
    @ApiOperation(value = "修改CsCouponReleased对象", notes = "修改优惠卷发放/领取记录", response = ApiResult.class)
    public ApiResult<Boolean> updateCsCouponReleased(@Valid @RequestBody CsCouponReleased csCouponReleased) throws Exception {
        boolean flag = csCouponReleasedService.updateCsCouponReleased(csCouponReleased);
        return ApiResult.result(flag);
    }

    /**
     * 删除优惠卷发放/领取记录
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:coupon:released:delete")
    @ApiOperation(value = "删除CsCouponReleased对象", notes = "删除优惠卷发放/领取记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsCouponReleased(@PathVariable("id") Long id) throws Exception {
        boolean flag = csCouponReleasedService.deleteCsCouponReleased(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除优惠卷发放/领取记录
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:coupon:released:delete")
    @ApiOperation(value = "批量删除CsCouponReleased对象", notes = "批量删除优惠卷发放/领取记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsCouponReleased(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csCouponReleasedService.deleteCsCouponReleaseds(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取优惠卷发放/领取记录
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:coupon:released:info")
    @ApiOperation(value = "获取CsCouponReleased对象详情", notes = "查看优惠卷发放/领取记录", response = CsCouponReleasedQueryVo.class)
    public ApiResult<CsCouponReleasedQueryVo> getCsCouponReleased(@PathVariable("id") Long id) throws Exception {
        CsCouponReleasedQueryVo csCouponReleasedQueryVo = csCouponReleasedService.getCsCouponReleasedById(id);
        return ApiResult.ok(csCouponReleasedQueryVo);
    }

    /**
     * 优惠卷发放/领取记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:coupon:released:page")
    @ApiOperation(value = "获取CsCouponReleased分页列表", notes = "优惠卷发放/领取记录分页列表", response = CsCouponReleasedQueryVo.class)
    public ApiResult<Paging<CsCouponReleasedQueryVo>> getCsCouponReleasedPageList(@Valid @RequestBody CsCouponReleasedQueryParam csCouponReleasedQueryParam) throws Exception {
        Paging<CsCouponReleasedQueryVo> paging = csCouponReleasedService.getCsCouponReleasedPageList(csCouponReleasedQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 优惠卷发放/领取记录修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:coupon:released:update")
    @ApiOperation(value = "修改CsCouponReleased状态", notes = "优惠卷发放/领取记录修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsCouponReleasedQueryParam csCouponReleasedQueryParam) throws Exception {
        return ApiResult.ok(csCouponReleasedService.updateStatus(csCouponReleasedQueryParam));
    }

    /**
     * 获取生效的优惠卷for wx根据wxuserId
     */
    @GetMapping("/getCouponForWx/{wxuserId}")
    @ApiOperation(value = "获取生效的优惠卷for wx根据wxuserId", notes = "获取生效的优惠卷for wx根据wxuserId", response = List.class)
    public ApiResult<List<CsCouponReleasedQueryVo>> getCouponForWx(@PathVariable("wxuserId") Long wxuserId) throws Exception {
        List<CsCouponReleasedQueryVo> list = csCouponReleasedService.getCsCouponReleasedByUserId(wxuserId);
        return ApiResult.ok(list);
    }

    /**
     * 领取领卷中心的优惠卷,只需要CouponId和WxuserId，每张优惠卷调用一次
     */
    @PostMapping("/saveCouponForCouponCenter")
    @ApiOperation(value = "领取领卷中心的优惠卷", notes = "领取领卷中心的优惠卷", response = Boolean.class)
    public ApiResult<Boolean> saveCouponForCouponCenter(@Valid @RequestBody CsCouponReleased csCouponReleased) throws Exception {
        return ApiResult.ok(csCouponReleasedService.saveCsCouponReleased(csCouponReleased));
    }
}

