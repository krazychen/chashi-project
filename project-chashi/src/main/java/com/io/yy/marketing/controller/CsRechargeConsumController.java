package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.service.CsRechargeConsumService;
import com.io.yy.marketing.param.CsRechargeConsumQueryParam;
import com.io.yy.marketing.vo.CsRechargeConsumQueryVo;
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
 * 充值消费 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Slf4j
@RestController
@RequestMapping("/csRechargeConsum")
@Api("充值消费 API")
public class CsRechargeConsumController extends BaseController {

    @Autowired
    private CsRechargeConsumService csRechargeConsumService;

    /**
     * 添加充值消费
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:recharge:consum:add")
    @ApiOperation(value = "添加CsRechargeConsum对象", notes = "添加充值消费", response = ApiResult.class)
    public ApiResult<Boolean> addCsRechargeConsum(@Valid @RequestBody CsRechargeConsum csRechargeConsum) throws Exception {
        boolean flag = csRechargeConsumService.saveCsRechargeConsum(csRechargeConsum);
        return ApiResult.result(flag);
    }

    /**
     * 修改充值消费
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:recharge:consum:update")
    @ApiOperation(value = "修改CsRechargeConsum对象", notes = "修改充值消费", response = ApiResult.class)
    public ApiResult<Boolean> updateCsRechargeConsum(@Valid @RequestBody CsRechargeConsum csRechargeConsum) throws Exception {
        boolean flag = csRechargeConsumService.updateCsRechargeConsum(csRechargeConsum);
        return ApiResult.result(flag);
    }

    /**
     * 删除充值消费
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:recharge:consum:delete")
    @ApiOperation(value = "删除CsRechargeConsum对象", notes = "删除充值消费", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsRechargeConsum(@PathVariable("id") Long id) throws Exception {
        boolean flag = csRechargeConsumService.deleteCsRechargeConsum(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除充值消费
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:recharge:consum:delete")
    @ApiOperation(value = "批量删除CsRechargeConsum对象", notes = "批量删除充值消费", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsRechargeConsum(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csRechargeConsumService.deleteCsRechargeConsums(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取充值消费
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:recharge:consum:info")
    @ApiOperation(value = "获取CsRechargeConsum对象详情", notes = "查看充值消费", response = CsRechargeConsumQueryVo.class)
    public ApiResult<CsRechargeConsumQueryVo> getCsRechargeConsum(@PathVariable("id") Long id) throws Exception {
        CsRechargeConsumQueryVo csRechargeConsumQueryVo = csRechargeConsumService.getCsRechargeConsumById(id);
        return ApiResult.ok(csRechargeConsumQueryVo);
    }

    /**
     * 充值消费分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:recharge:consum:page")
    @ApiOperation(value = "获取CsRechargeConsum分页列表", notes = "充值消费分页列表", response = CsRechargeConsumQueryVo.class)
    public ApiResult<Paging<CsRechargeConsumQueryVo>> getCsRechargeConsumPageList(@Valid @RequestBody CsRechargeConsumQueryParam csRechargeConsumQueryParam) throws Exception {
        Paging<CsRechargeConsumQueryVo> paging = csRechargeConsumService.getCsRechargeConsumPageList(csRechargeConsumQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 充值消费修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:recharge:consum:update")
    @ApiOperation(value = "修改CsRechargeConsum状态", notes = "充值消费修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsRechargeConsumQueryParam csRechargeConsumQueryParam) throws Exception {
        return ApiResult.ok(csRechargeConsumService.updateStatus(csRechargeConsumQueryParam));
    }


}

