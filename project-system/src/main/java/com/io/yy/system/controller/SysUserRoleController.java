package com.io.yy.system.controller;

import com.io.yy.system.entity.SysUserRole;
import com.io.yy.system.param.SysRoleMenuQueryParam;
import com.io.yy.system.param.SysUserRolePLQueryParam;
import com.io.yy.system.param.SysUserRoleQueryParam;
import com.io.yy.system.service.SysUserRoleService;
import com.io.yy.system.vo.SysUserRoleQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 用户和角色关联表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
@Slf4j
@RestController
@RequestMapping("/sysUserRole")
@Api("用户和角色关联表 API")
public class SysUserRoleController extends BaseController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 添加用户和角色关联表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:user:role:add")
    @ApiOperation(value = "添加SysUserRole对象", notes = "添加用户和角色关联表", response = ApiResult.class)
    public ApiResult<Boolean> addSysUserRole(@Valid @RequestBody SysUserRole sysUserRole) throws Exception {
        boolean flag = sysUserRoleService.saveSysUserRole(sysUserRole);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户和角色关联表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:role:update")
    @ApiOperation(value = "修改SysUserRole对象", notes = "修改用户和角色关联表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysUserRole(@Valid @RequestBody SysUserRole sysUserRole) throws Exception {
        boolean flag = sysUserRoleService.updateSysUserRole(sysUserRole);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户和角色关联表
     */
    @PostMapping("/deletes")
    @RequiresPermissions("sys:user:role:delete")
    @ApiOperation(value = "删除SysUserRole对象", notes = "删除用户和角色关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUserRole(@Valid @RequestBody Map<String,Object> map) throws Exception {
        boolean flag = sysUserRoleService.deleteSysUserRole(map);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除用户和角色关联表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:role:delete")
    @ApiOperation(value = "批量删除SysUserRole对象", notes = "批量删除用户和角色关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUserRole(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysUserRoleService.deleteSysUserRoles(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取用户和角色关联表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:user:role:info")
    @ApiOperation(value = "获取SysUserRole对象详情", notes = "查看用户和角色关联表", response = SysUserRoleQueryVo.class)
    public ApiResult<SysUserRoleQueryVo> getSysUserRole(@PathVariable("id") Long id) throws Exception {
        SysUserRoleQueryVo sysUserRoleQueryVo = sysUserRoleService.getSysUserRoleById(id);
        return ApiResult.ok(sysUserRoleQueryVo);
    }

    /**
     * 用户和角色关联表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:user:role:page")
    @ApiOperation(value = "获取SysUserRole分页列表", notes = "用户和角色关联表分页列表", response = SysUserRoleQueryVo.class)
    public ApiResult<Paging<SysUserRoleQueryVo>> getSysUserRolePageList(@Valid @RequestBody SysUserRoleQueryParam sysUserRoleQueryParam) throws Exception {
        Paging<SysUserRoleQueryVo> paging = sysUserRoleService.getSysUserRolePageList(sysUserRoleQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 根据用户id查询角色集合
     */
    @GetMapping("/findUserById/{userId}")
    @RequiresPermissions("sys:user:role:findUserById")
    @ApiOperation(value = "获取SysUserRole对象集合", notes = "查看用户和角色关联表", response = SysUserRoleQueryVo.class)
    public ApiResult<SysUserRoleQueryVo> findUserById(@PathVariable("userId") Long userId) throws Exception {
        String roleIds = sysUserRoleService.findUserById(userId);
        return ApiResult.ok(roleIds);
    }

    /**
     * 批量新增用户角色
     */
    @PostMapping("/addRoles")
    @RequiresPermissions("sys:user:addRoles")
    @ApiOperation(value = "修改SysUserRole对象", notes = "批量修改系统用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addRoles(@RequestBody Map<String, Object> params) throws Exception {
        boolean flag = sysUserRoleService.addRoles(params.get("userId").toString(),params.get("roleId").toString());
        return ApiResult.result(flag);
    }

    /**
     * 批量新增角色用户
     */
    @PostMapping("/addRolesUser")
    @RequiresPermissions("sys:user:addRoles")
    @ApiOperation(value = "修改SysUserRole对象", notes = "批量修改系统用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addRolesUser(@RequestBody SysUserRolePLQueryParam sysUserRolePLQueryParam) throws Exception {
        boolean flag = sysUserRoleService.addRolesUser(sysUserRolePLQueryParam);
        return ApiResult.result(flag);
    }

    /**
     * 单个新增用户角色
     */
    @PostMapping("/addRole")
    @RequiresPermissions("sys:user:addRole")
    @ApiOperation(value = "修改SysUserRole对象", notes = "单个修改系统用户角色", response = ApiResult.class)
    public ApiResult<Boolean> addRole(@RequestBody Map<String, Object> params) throws Exception {
        boolean flag = sysUserRoleService.addRole(params.get("userId").toString(),params.get("roleId").toString());
        return ApiResult.result(flag);
    }

    /**
     * 一个角色关联多个用户
     * @param sysUserRoleQueryParam
     * @return
     * @throws Exception
     */
    @PostMapping("/addRoleAndUser")
    @RequiresPermissions("sys:user:addRole")
    @ApiOperation(value = "批量关联角色和用户", notes = "批量关联角色和用户", response = ApiResult.class)
    public ApiResult<Boolean> addRoleAndUser(@Valid @RequestBody SysUserRoleQueryParam sysUserRoleQueryParam) throws Exception {
        boolean flag = sysUserRoleService.addRoleAndUser(sysUserRoleQueryParam);
        return ApiResult.result(flag);
    }


    /**
     * 根据用户id获取角色信息
     */
    @GetMapping("/getUserRoleByUserId/{userId}")
    @RequiresPermissions("sys:user:role:findUserById")
    @ApiOperation(value = "根据用户id获取角色信息", notes = "根据用户id获取角色信息", response = SysUserRoleQueryVo.class)
    public ApiResult<List<SysUserRoleQueryVo>> getUserRoleByUserId(@PathVariable("userId") Long userId) throws Exception {
        return ApiResult.ok(sysUserRoleService.getUserRoleByUserId(userId));
    }
}

