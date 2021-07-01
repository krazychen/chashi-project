package com.io.yy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.util.JwtUtil;
import com.io.yy.system.entity.SysMsg;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.param.StudentSysMsgQueryParam;
import com.io.yy.system.service.SysMsgRecordService;
import com.io.yy.system.service.SysMsgService;
import com.io.yy.system.param.SysMsgQueryParam;
import com.io.yy.system.vo.SysMsgQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.system.vo.SysUserVo;
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
 * 系统消息表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Slf4j
@RestController
@RequestMapping("/sysMsg")
@Api("系统消息表 API")
public class SysMsgController extends BaseController {

    @Autowired
    private SysMsgService sysMsgService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMsgRecordService sysMsgRecordService;

    /**
     * 添加系统消息表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:msg:add")
    @ApiOperation(value = "添加SysMsg对象", notes = "添加系统消息表", response = ApiResult.class)
    public ApiResult<Boolean> addSysMsg(@Valid @RequestBody SysMsg sysMsg) throws Exception {
        boolean flag = sysMsgService.saveSysMsg(sysMsg);
        return ApiResult.result(flag);
    }

    /**
     * 修改系统消息表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:msg:update")
    @ApiOperation(value = "修改SysMsg对象", notes = "修改系统消息表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysMsg(@Valid @RequestBody SysMsg sysMsg) throws Exception {
        boolean flag = sysMsgService.updateSysMsg(sysMsg);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统消息表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:msg:delete")
    @ApiOperation(value = "删除SysMsg对象", notes = "删除系统消息表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysMsg(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysMsgService.deleteSysMsg(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除系统消息表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:msg:delete")
    @ApiOperation(value = "批量删除SysMsg对象", notes = "批量删除系统消息表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysMsg(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysMsgService.deleteSysMsgs(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取系统消息表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:msg:info")
    @ApiOperation(value = "获取SysMsg对象详情", notes = "查看系统消息表", response = SysMsgQueryVo.class)
    public ApiResult<SysMsgQueryVo> getSysMsg(@PathVariable("id") Long id) throws Exception {
        SysMsgQueryVo sysMsgQueryVo = sysMsgService.getSysMsgById(id);
        return ApiResult.ok(sysMsgQueryVo);
    }

    /**
     * 系统消息表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:msg:page")
    @ApiOperation(value = "获取SysMsg分页列表", notes = "系统消息表分页列表", response = SysMsgQueryVo.class)
    public ApiResult<Paging<SysMsgQueryVo>> getSysMsgPageList(@Valid @RequestBody SysMsgQueryParam sysMsgQueryParam) throws Exception {
        Paging<SysMsgQueryVo> paging = sysMsgService.getSysMsgPageList(sysMsgQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 发布系统消息表
     */
    @PostMapping("/publishMsg/{id}")
    @RequiresPermissions("sys:msg:publishMsg")
    @ApiOperation(value = "发布SysMsg对象消息", notes = "发布系统消息", response = ApiResult.class)
    public ApiResult<Boolean> publishMsg(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysMsgService.publish(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/findByUser")
    @RequiresPermissions("sys:msg:findByUser")
    @ApiOperation(value = "获取SysMsg对象详情", notes = "查看系统消息表", response = SysUserVo.class)
    public ApiResult<SysUserVo> findByUser() throws Exception {
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        return ApiResult.ok(sysUser);
    }

    /**
     * 撤销系统消息表
     */
    @PostMapping("/cancelMsg/{id}")
    @RequiresPermissions("sys:msg:cancelMsg")
    @ApiOperation(value = "撤销SysMsg对象", notes = "撤销系统消息表", response = ApiResult.class)
    public ApiResult<Boolean> cancelMsg(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysMsgService.cancelMsg(id);
        return ApiResult.result(flag);
    }

//    /**
//     * 撤销系统消息表
//     */
//    @PostMapping("/sendMsg")
//    //@RequiresPermissions("sys:msg:send")
//    @ApiOperation(value = "下发通知", notes = "下发通知", response = ApiResult.class)
//    public ApiResult<Boolean> sendMsg(@RequestBody HwHomework hwHomework) throws Exception {
//        return ApiResult.ok(sysMsgService.sendMsg(hwHomework,null));
//    }

    /**
     * 学员前台消息列表API
     */
    @PostMapping("/getStudentPageList")
    @RequiresPermissions("sys:msg:getStudentPageList")
    @ApiOperation(value = "获取SysMsg分页列表", notes = "系统消息表分页列表", response = SysMsgQueryVo.class)
    public ApiResult<Paging<SysMsgQueryVo>> getStudentPageList(@Valid @RequestBody StudentSysMsgQueryParam studentSysMsgQueryParam) throws Exception {
        //获取token中的用户id
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        studentSysMsgQueryParam.setReceiveUserId(sysUser.getId().toString());
        Paging<SysMsgQueryVo> paging = sysMsgService.getStudentPageList(studentSysMsgQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 查询消息记录表中未读取的消息记录数量
     */
    @PostMapping("/findMsgRecordCount")
    @RequiresPermissions("sys:msg:findMsgRecordCount")
    @ApiOperation(value = "查询SysMsgRecord对象", notes = "查询消息记录表中未读取的消息记录数量", response = ApiResult.class)
    public ApiResult<Integer> findMsgRecordCount() throws Exception {
        //获取token中的用户id
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        int count = sysMsgRecordService.findMsgRecordCount(sysUser.getId());
        return ApiResult.ok(count);
    }

}

