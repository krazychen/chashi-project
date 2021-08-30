package com.io.yy.marketing.controller;

import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.param.CsMembercardConsumQueryParam;
import com.io.yy.marketing.vo.CsMembercardConsumQueryVo;
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

/**
 * <pre>
 * 会员卡消费记录 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-24
 */
@Slf4j
@RestController
@RequestMapping("/csMembercardConsum")
@Api("会员卡消费记录 API")
public class CsMembercardConsumController extends BaseController {

    @Autowired
    private CsMembercardConsumService csMembercardConsumService;

    /**
     * 添加会员卡消费记录
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:membercard:consum:add")
    @ApiOperation(value = "添加CsMembercardConsum对象", notes = "添加会员卡消费记录", response = ApiResult.class)
    public ApiResult<Boolean> addCsMembercardConsum(@Valid @RequestBody CsMembercardConsum csMembercardConsum) throws Exception {
        boolean flag = csMembercardConsumService.saveCsMembercardConsum(csMembercardConsum);
        return ApiResult.result(flag);
    }

    /**
     * 修改会员卡消费记录
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:membercard:consum:update")
    @ApiOperation(value = "修改CsMembercardConsum对象", notes = "修改会员卡消费记录", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMembercardConsum(@Valid @RequestBody CsMembercardConsum csMembercardConsum) throws Exception {
        boolean flag = csMembercardConsumService.updateCsMembercardConsum(csMembercardConsum);
        return ApiResult.result(flag);
    }

    /**
     * 删除会员卡消费记录
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:membercard:consum:delete")
    @ApiOperation(value = "删除CsMembercardConsum对象", notes = "删除会员卡消费记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMembercardConsum(@PathVariable("id") Long id) throws Exception {
        boolean flag = csMembercardConsumService.deleteCsMembercardConsum(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除会员卡消费记录
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:membercard:consum:delete")
    @ApiOperation(value = "批量删除CsMembercardConsum对象", notes = "批量删除会员卡消费记录", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMembercardConsum(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csMembercardConsumService.deleteCsMembercardConsums(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取会员卡消费记录
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:membercard:consum:info")
    @ApiOperation(value = "获取CsMembercardConsum对象详情", notes = "查看会员卡消费记录", response = CsMembercardConsumQueryVo.class)
    public ApiResult<CsMembercardConsumQueryVo> getCsMembercardConsum(@PathVariable("id") Long id) throws Exception {
        CsMembercardConsumQueryVo csMembercardConsumQueryVo = csMembercardConsumService.getCsMembercardConsumById(id);
        return ApiResult.ok(csMembercardConsumQueryVo);
    }

    /**
     * 会员卡消费记录分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:membercard:consum:page")
    @ApiOperation(value = "获取CsMembercardConsum分页列表", notes = "会员卡消费记录分页列表", response = CsMembercardConsumQueryVo.class)
    public ApiResult<Paging<CsMembercardConsumQueryVo>> getCsMembercardConsumPageList(@Valid @RequestBody CsMembercardConsumQueryParam csMembercardConsumQueryParam) throws Exception {
        Paging<CsMembercardConsumQueryVo> paging = csMembercardConsumService.getCsMembercardConsumPageList(csMembercardConsumQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 会员卡消费记录修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:membercard:consum:update")
    @ApiOperation(value = "修改CsMembercardConsum状态", notes = "会员卡消费记录修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsMembercardConsumQueryParam csMembercardConsumQueryParam) throws Exception {
        return ApiResult.ok(csMembercardConsumService.updateStatus(csMembercardConsumQueryParam));
    }


}

