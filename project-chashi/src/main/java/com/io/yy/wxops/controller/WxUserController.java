package com.io.yy.wxops.controller;

import com.io.yy.wxops.entity.WxUser;
import com.io.yy.wxops.param.WxLoginQueryParam;
import com.io.yy.wxops.service.WxUserService;
import com.io.yy.wxops.param.WxUserQueryParam;
import com.io.yy.wxops.vo.WxUserQueryVo;
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
 * 微信用户 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-06
 */
@Slf4j
@RestController
@RequestMapping("/wxUser")
@Api("微信用户 API")
public class WxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;

    /**
     * 添加微信用户
     */
    @PostMapping("/add")
    @RequiresPermissions("wx:user:add")
    @ApiOperation(value = "添加WxUser对象", notes = "添加微信用户", response = ApiResult.class)
    public ApiResult<Boolean> addWxUser(@Valid @RequestBody WxUser wxUser) throws Exception {
        boolean flag = wxUserService.saveWxUser(wxUser);
        return ApiResult.result(flag);
    }

    /**
     * 修改微信用户
     */
    @PostMapping("/update")
    @RequiresPermissions("wx:user:update")
    @ApiOperation(value = "修改WxUser对象", notes = "修改微信用户", response = ApiResult.class)
    public ApiResult<Boolean> updateWxUser(@Valid @RequestBody WxUser wxUser) throws Exception {
        boolean flag = wxUserService.updateWxUser(wxUser);
        return ApiResult.result(flag);
    }

    /**
     * 删除微信用户
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("wx:user:delete")
    @ApiOperation(value = "删除WxUser对象", notes = "删除微信用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteWxUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = wxUserService.deleteWxUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除微信用户
     */
    @PostMapping("/delete")
    @RequiresPermissions("wx:user:delete")
    @ApiOperation(value = "批量删除WxUser对象", notes = "批量删除微信用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteWxUser(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = wxUserService.deleteWxUsers(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取微信用户
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:user:info")
    @ApiOperation(value = "获取WxUser对象详情", notes = "查看微信用户", response = WxUserQueryVo.class)
    public ApiResult<WxUserQueryVo> getWxUser(@PathVariable("id") Long id) throws Exception {
        WxUserQueryVo wxUserQueryVo = wxUserService.getWxUserById(id);
        return ApiResult.ok(wxUserQueryVo);
    }

    /**
     * 微信用户分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("wx:user:page")
    @ApiOperation(value = "获取WxUser分页列表", notes = "微信用户分页列表", response = WxUserQueryVo.class)
    public ApiResult<Paging<WxUserQueryVo>> getWxUserPageList(@Valid @RequestBody WxUserQueryParam wxUserQueryParam) throws Exception {
        Paging<WxUserQueryVo> paging = wxUserService.getWxUserPageList(wxUserQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 微信用户修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("wx:user:update")
    @ApiOperation(value = "修改WxUser状态", notes = "微信用户修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody WxUserQueryParam wxUserQueryParam) throws Exception {
        return ApiResult.ok(wxUserService.updateStatus(wxUserQueryParam));
    }

    /**
     * 微信登录
     */
    @PostMapping("/wxLogin")
    public ApiResult<WxUserQueryVo> wxLogin(@RequestBody WxLoginQueryParam wxLoginQueryParam) throws Exception {
        WxUserQueryVo wsUserQueryVo = wxUserService.wxLogin(wxLoginQueryParam);
        return ApiResult.ok(wsUserQueryVo);
    }

    /**
     * 获取微信用户for wx
     */
    @GetMapping("/infoForWx/{id}")
    @ApiOperation(value = "获取WxUser对象详情", notes = "查看微信用户", response = WxUserQueryVo.class)
    public ApiResult<WxUserQueryVo> getWxUserForWx(@PathVariable("openid") Long openid) throws Exception {
        WxUserQueryVo wxUserQueryVo = wxUserService.getWxUserByOpenid(openid);
        return ApiResult.ok(wxUserQueryVo);
    }
}

