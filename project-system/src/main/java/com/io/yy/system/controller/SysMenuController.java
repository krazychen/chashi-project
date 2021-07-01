package com.io.yy.system.controller;

import com.io.yy.system.entity.SysMenu;
import com.io.yy.system.param.SysMenuTypeQueryParam;
import com.io.yy.system.service.SysMenuService;
import com.io.yy.system.param.SysMenuQueryParam;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import com.io.yy.common.vo.Paging;
import com.io.yy.common.param.IdParam;

/**
 * <pre>
 * 菜单表 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Slf4j
@RestController
@RequestMapping("/sysMenu")
@Api("菜单表 API")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 添加菜单表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:menu:add")
    @ApiOperation(value = "添加SysMenu对象", notes = "添加菜单表", response = ApiResult.class)
    public ApiResult<Boolean> addSysMenu(@Valid @RequestBody SysMenu sysMenu) throws Exception {
        boolean flag = sysMenuService.saveSysMenu(sysMenu);
        return ApiResult.result(flag);
    }

    /**
     * 修改菜单表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    @ApiOperation(value = "修改SysMenu对象", notes = "修改菜单表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysMenu(@Valid @RequestBody SysMenu sysMenu) throws Exception {
        boolean flag = sysMenuService.updateSysMenu(sysMenu);
        return ApiResult.result(flag);
    }

    /**
     * 删除菜单表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:menu:delete")
    @ApiOperation(value = "删除SysMenu对象", notes = "删除菜单表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysMenu(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysMenuService.deleteSysMenu(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除菜单表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    @ApiOperation(value = "批量删除SysMenu对象", notes = "批量删除菜单表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysMenu(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysMenuService.deleteSysMenus(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取菜单表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:menu:info")
    @ApiOperation(value = "获取SysMenu对象详情", notes = "查看菜单表", response = SysMenuQueryVo.class)
    public ApiResult<SysMenuQueryVo> getSysMenu(@PathVariable("id") String id) throws Exception {
        SysMenuQueryVo sysMenuQueryVo = sysMenuService.getSysMenuById(id);
        return ApiResult.ok(sysMenuQueryVo);
    }

    /**
     * 菜单表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:menu:page")
    @ApiOperation(value = "获取SysMenu分页列表", notes = "菜单表分页列表", response = SysMenuQueryVo.class)
    public ApiResult<Paging<SysMenuQueryVo>> getSysMenuPageList(@Valid @RequestBody SysMenuQueryParam sysMenuQueryParam) throws Exception {
        Paging<SysMenuQueryVo> paging = sysMenuService.getSysMenuPageList(sysMenuQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 菜单表分页列表
     */
    @PostMapping("/getSysMenuSimplifyPageList")
    @RequiresPermissions("sys:menu:page")
    @ApiOperation(value = "获取SysMenu分页列表", notes = "菜单表分页列表", response = SysMenuQueryVo.class)
    public ApiResult<List<SysMenuTreeQueryVo>> getSysMenuSimplifyPageList(@Valid @RequestBody SysMenuTypeQueryParam sysMenuTypeQueryParam) throws Exception {
        List<SysMenuTreeQueryVo> list = sysMenuService.getSysMenuSimplifyPageList(sysMenuTypeQueryParam);
        return ApiResult.ok(list);
    }

    /**
     * 根据用户id获取用户关联的菜单
     * @param map
     * @return
     */

    /**
     * 角色关联的菜单
     */
    @PostMapping("/getSysMenuByRoleId")
    @RequiresPermissions("sys:menu:role:list")
    @ApiOperation(value = "角色关联的菜单", notes = "角色关联的菜单", response = SysMenuQueryVo.class)
    public ApiResult<List<SysMenuTreeQueryVo>> getSysMenuByRoleId(@Valid @RequestBody Map<String,Object> map) throws Exception {
        List<SysMenuTreeQueryVo> list = sysMenuService.getSysMenuByRoleId(map);
        return ApiResult.ok(list);
    }

    /**
     * 用户关联的菜单
     */
    @PostMapping("/getSysMenuByUserId")
    @ApiOperation(value = "用户关联的菜单", notes = "用户关联的菜单", response = SysMenuQueryVo.class)
    public ApiResult<List<SysMenuQueryVo>> getSysMenuByUserId(@Valid @RequestBody String userId) throws Exception {
        List<SysMenuQueryVo> list = sysMenuService.getSysMenuListByUserId(userId);
        return ApiResult.ok(list);
    }
}

