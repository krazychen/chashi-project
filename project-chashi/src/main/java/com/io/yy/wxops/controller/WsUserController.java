package com.io.yy.wxops.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.param.SysSchoolParam;
import com.io.yy.system.service.SysAreaService;
import com.io.yy.system.service.SysSchoolCollegeService;
import com.io.yy.system.service.SysSchoolService;
import com.io.yy.system.service.SysUserService;
import com.io.yy.system.vo.SchoolCollegeListQueryVo;
import com.io.yy.system.vo.SchoolListQueryVo;
import com.io.yy.system.vo.SysAreaQueryVo;
import com.io.yy.system.vo.SysUserVo;
import com.io.yy.wxops.param.WxLoginQueryParam;
import com.io.yy.wxops.service.WsUserService;
import com.io.yy.wxops.vo.WsUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 用户信息表 前端控制器
 * </pre>
 *
 * @author wuzhixiong
 * @since 2020-07-11
 */
@Slf4j
@RestController
@RequestMapping("/wsUser")
@Api("用户信息表 API")
public class WsUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WsUserService wsUserService;

    /**
     * 获取用户信息表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("ws:user:info")
    @ApiOperation(value = "获取WsUser对象详情", notes = "查看用户信息表", response = WsUserQueryVo.class)
    public ApiResult<WsUserQueryVo> getWsUser(@PathVariable("id") Long id) throws Exception {
        SysUserVo sysUserVo = sysUserService.getSysUserById(id);
        WsUserQueryVo wsUserQueryVo = new WsUserQueryVo();
        BeanUtils.copyProperties(sysUserVo,wsUserQueryVo);
        return ApiResult.ok(wsUserQueryVo);
    }

    /**
     * 微信登录
     */
    @PostMapping("/wxLogin")
    public ApiResult<WsUserQueryVo> wxLogin(@RequestBody WxLoginQueryParam wxLoginQueryParam) throws Exception {
        WsUserQueryVo wsUserQueryVo = wsUserService.wxLogin(wxLoginQueryParam);
        return ApiResult.ok(wsUserQueryVo);
    }

}

