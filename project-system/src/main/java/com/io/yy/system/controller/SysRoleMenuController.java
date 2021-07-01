package com.io.yy.system.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysRoleMenu;
import com.io.yy.system.param.SysRoleMenuQueryParam;
import com.io.yy.system.service.SysRoleMenuService;
import com.io.yy.system.vo.SysRoleMenuQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 角色与菜单关联表 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-03
 */
@Slf4j
@RestController
@RequestMapping("/sysRoleMenu")
@Api("角色与菜单关联表 API")
public class SysRoleMenuController extends BaseController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 添加角色与菜单关联表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:role:menu:add")
    @ApiOperation(value = "添加SysRoleMenu对象", notes = "添加角色与菜单关联表", response = ApiResult.class)
    public ApiResult<Boolean> addSysRoleMenu(@Valid @RequestBody SysRoleMenu sysRoleMenu) throws Exception {
        boolean flag = sysRoleMenuService.saveSysRoleMenu(sysRoleMenu);
        return ApiResult.result(flag);
    }

    /**
     * 修改角色与菜单关联表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:role:menu:update")
    @ApiOperation(value = "修改SysRoleMenu对象", notes = "修改角色与菜单关联表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysRoleMenu(@Valid @RequestBody SysRoleMenu sysRoleMenu) throws Exception {
        boolean flag = sysRoleMenuService.updateSysRoleMenu(sysRoleMenu);
        return ApiResult.result(flag);
    }

    /**
     * 删除角色与菜单关联表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:role:menu:delete")
    @ApiOperation(value = "删除SysRoleMenu对象", notes = "删除角色与菜单关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysRoleMenu(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysRoleMenuService.deleteSysRoleMenu(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除角色与菜单关联表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:menu:delete")
    @ApiOperation(value = "批量删除SysRoleMenu对象", notes = "批量删除角色与菜单关联表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysRoleMenu(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysRoleMenuService.deleteSysRoleMenus(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取角色与菜单关联表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:role:menu:info")
    @ApiOperation(value = "获取SysRoleMenu对象详情", notes = "查看角色与菜单关联表", response = SysRoleMenuQueryVo.class)
    public ApiResult<SysRoleMenuQueryVo> getSysRoleMenu(@PathVariable("id") Long id) throws Exception {
        SysRoleMenuQueryVo sysRoleMenuQueryVo = sysRoleMenuService.getSysRoleMenuById(id);
        return ApiResult.ok(sysRoleMenuQueryVo);
    }

    /**
     * 角色与菜单关联表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:role:menu:page")
    @ApiOperation(value = "获取SysRoleMenu分页列表", notes = "角色与菜单关联表分页列表", response = SysRoleMenuQueryVo.class)
    public ApiResult<Paging<SysRoleMenuQueryVo>> getSysRoleMenuPageList(@Valid @RequestBody SysRoleMenuQueryParam sysRoleMenuQueryParam) throws Exception {
        Paging<SysRoleMenuQueryVo> paging = sysRoleMenuService.getSysRoleMenuPageList(sysRoleMenuQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 批量关联角色和菜单
     */
    @PostMapping("/addRoleAndMenu")
    @RequiresPermissions("sys:role:menu:add")
    @ApiOperation(value = "批量关联角色和菜单", notes = "批量关联角色和菜单", response = ApiResult.class)
    public ApiResult<Boolean> addRoleAndMenu(@Valid @RequestBody SysRoleMenuQueryParam sysRoleMenuQueryParam) throws Exception {
        boolean flag = sysRoleMenuService.addRoleAndMenu(sysRoleMenuQueryParam);
        return ApiResult.ok(flag);
    }

}

