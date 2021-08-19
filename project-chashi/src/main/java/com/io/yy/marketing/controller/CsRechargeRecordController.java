package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsRechargeRecord;
import com.io.yy.marketing.service.CsRechargeRecordService;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.vo.CsRechargeRecordQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.util.UUIDUtil;
import com.io.yy.util.lang.DateUtils;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * 充值记录 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Slf4j
@RestController
@RequestMapping("/csRechargeRecord")
@Api("充值记录 API")
public class CsRechargeRecordController extends BaseController {

    @Autowired
    private CsRechargeRecordService csRechargeRecordService;

    @Autowired
    private WxUserService wxUserService;

    /**
     * 添加充值记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:recharge:record:add")
    @ApiOperation(value = "添加CsRechargeRecord对象", notes = "添加充值记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsRechargeRecord(@Valid @RequestBody CsRechargeRecord csRechargeRecord) throws Exception {
        csRechargeRecord.setPaymentStatus(2);
        csRechargeRecord.setSourceType(0);
        csRechargeRecord.setOrderDate(new Date());
        csRechargeRecord.setOrderName("Recharge-"+csRechargeRecord.getRechargeAmount()+DateUtils.getYYYYMMDDHHMMSS(csRechargeRecord.getOrderDate())+'-'+ UUIDUtil.getUUID());
        csRechargeRecord.setRechargeFinal(csRechargeRecord.getRechargeAmount()+csRechargeRecord.getRechargeGived());
        csRechargeRecord.setIntegral(csRechargeRecord.getRechargeAmount().intValue());
        boolean flag = csRechargeRecordService.saveCsRechargeRecord(csRechargeRecord);

        //更新用户余额和积分
        WxUserQueryParam wxUserQueryParam = new WxUserQueryParam();
        wxUserQueryParam.setId(csRechargeRecord.getWxuserId());
        wxUserQueryParam.setBalance(csRechargeRecord.getRechargeFinal());
        wxUserQueryParam.setIntegral(csRechargeRecord.getIntegral());
        wxUserService.updateBalanceAIntegral(wxUserQueryParam);

        return ApiResult.result(flag);
    }

    /**
     * 修改充值记录
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:recharge:record:update")
    @ApiOperation(value = "修改CsRechargeRecord对象", notes = "修改充值记录", response = ApiResult.class)
    public ApiResult<Boolean> updateCsRechargeRecord(@Valid @RequestBody CsRechargeRecord csRechargeRecord) throws Exception {
        boolean flag = csRechargeRecordService.updateCsRechargeRecord(csRechargeRecord);
        return ApiResult.result(flag);
    }

    /**
     * 删除充值记录
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:recharge:record:delete")
    @ApiOperation(value = "删除CsRechargeRecord对象", notes = "删除充值记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsRechargeRecord(@PathVariable("id") Long id) throws Exception {
        boolean flag = csRechargeRecordService.deleteCsRechargeRecord(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除充值记录
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:recharge:record:delete")
    @ApiOperation(value = "批量删除CsRechargeRecord对象", notes = "批量删除充值记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsRechargeRecord(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csRechargeRecordService.deleteCsRechargeRecords(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取充值记录
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:recharge:record:info")
    @ApiOperation(value = "获取CsRechargeRecord对象详情", notes = "查看充值记录", response = CsRechargeRecordQueryVo.class)
    public ApiResult<CsRechargeRecordQueryVo> getCsRechargeRecord(@PathVariable("id") Long id) throws Exception {
        CsRechargeRecordQueryVo csRechargeRecordQueryVo = csRechargeRecordService.getCsRechargeRecordById(id);
        return ApiResult.ok(csRechargeRecordQueryVo);
    }

    /**
     * 充值记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:recharge:record:page")
    @ApiOperation(value = "获取CsRechargeRecord分页列表", notes = "充值记录分页列表", response = CsRechargeRecordQueryVo.class)
    public ApiResult<Paging<CsRechargeRecordQueryVo>> getCsRechargeRecordPageList(@Valid @RequestBody CsRechargeRecordQueryParam csRechargeRecordQueryParam) throws Exception {
        Paging<CsRechargeRecordQueryVo> paging = csRechargeRecordService.getCsRechargeRecordPageList(csRechargeRecordQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 充值记录修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:recharge:record:update")
    @ApiOperation(value = "修改CsRechargeRecord状态", notes = "充值记录修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsRechargeRecordQueryParam csRechargeRecordQueryParam) throws Exception {
        return ApiResult.ok(csRechargeRecordService.updateStatus(csRechargeRecordQueryParam));
    }


}

