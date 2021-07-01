/*
 * Copyright 2019-2029 kris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.yy.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.util.JwtUtil;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.param.*;
import com.io.yy.system.service.SysRoleService;
import com.io.yy.system.service.SysUserService;
import com.io.yy.system.vo.SysRoleQueryVo;
import com.io.yy.system.vo.SysUserQueryVo;
import com.io.yy.system.vo.SysUserVo;
import com.io.yy.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 系统用户 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
@Api("系统用户 API")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 添加系统用户
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    @ApiOperation(value = "添加SysUser对象", notes = "添加系统用户", response = ApiResult.class)
    public ApiResult<Boolean> addSysUser(@Valid @RequestBody SysUser sysUser) throws Exception {
        boolean flag = sysUserService.saveSysUser(sysUser);
        return ApiResult.result(flag);
    }


    /**
     * 修改系统用户
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改SysUser对象", notes = "修改系统用户", response = ApiResult.class)
    public ApiResult<Boolean> updateSysUser(@Valid @RequestBody SysUser sysUser) throws Exception {
        boolean flag = sysUserService.updateSysUser(sysUser);
        return ApiResult.result(flag);
    }

    /**
     * 删除系统用户
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "删除SysUser对象", notes = "删除系统用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.deleteSysUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取系统用户
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation(value = "获取SysUser对象详情", notes = "查看系统用户", response = SysUserVo.class)
    public ApiResult<SysUserVo> getSysUser(@PathVariable("id") Long id) throws Exception {
        SysUserVo sysUserVo = sysUserService.getSysUserById(id);
        return ApiResult.ok(sysUserVo);
    }

    /**
     * 系统用户分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:user:page")
    @ApiOperation(value = "获取SysUser分页列表", notes = "系统用户分页列表", response = SysUserQueryVo.class)
    public ApiResult<Paging<SysUserQueryVo>> getSysUserPageList(@Valid @RequestBody SysUserQueryParam sysUserQueryParam) throws Exception {
        Paging<SysUserQueryVo> paging = sysUserService.getSysUserPageList(sysUserQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 角色id所关联的所有用户getPageList
     */
    @PostMapping("/getSysRoleAndUserList")
    @RequiresPermissions("sys:user:role:alllist")
    @ApiOperation(value = "获取所有关联了某个角色的SysUser列表", notes = "map中有一个参数，为id", response = SysUserQueryVo.class)
    public ApiResult<List<SysUserQueryVo>> getSysRoleAndUserList(@Valid @RequestBody Map<String, Object> map) throws Exception {
        List<SysUserQueryVo> list = sysUserService.getSysRoleAndUserList(map);
        return ApiResult.ok(list);
    }

    /**
     * 角色id所关联的所有用户的分页
     */
    @PostMapping("/getSysRoleAndUserPageList")
    @RequiresPermissions("sys:user:page")
    @ApiOperation(value = "获取关联了某个角色的SysUser分页列表", notes = "系统用户分页列表", response = SysUserQueryVo.class)
    public ApiResult<List<SysUserQueryVo>> getSysRoleAndUserPageList(@Valid @RequestBody SysUserQueryParam sysUserQueryParam) throws Exception {
        Paging<SysUserQueryVo> paging = sysUserService.getSysRoleAndUserPageList(sysUserQueryParam);
        return ApiResult.ok(paging);
    }


    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    @RequiresPermissions("sys:user:updatePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码", response = ApiResult.class)
    public ApiResult<Boolean> updatePassword(@Valid @RequestBody UpdatePasswordParam updatePasswordParam) {
        try {
            boolean flag = sysUserService.updatePassword(updatePasswordParam);
            return ApiResult.ok(flag);
        } catch (Exception e) {
            return ApiResult.ok(false,e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/noWx/{id}")
    @RequiresPermissions("sys:user:updatePassword")
    @ApiOperation(value = "解绑微信", notes = "解绑微信", response = ApiResult.class)
    public ApiResult<Boolean> noWx(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.noWx(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量解绑微信
     */
    @PostMapping("/nowxs")
    @RequiresPermissions("sys:user:updatePassword")
    @ApiOperation(value = "批量解绑微信", notes = "批量解绑微信", response = ApiResult.class)
    public ApiResult<Boolean> nowxs(@Valid @RequestBody List<Long> idList) throws Exception {
        boolean flag = sysUserService.nowxs(idList);
        return ApiResult.result(flag);
    }

    /**
     * 修改头像
     */
    @PostMapping("/uploadHead")
    @RequiresPermissions("sys:user:uploadHead")
    @ApiOperation(value = "修改头像", notes = "修改头像", response = ApiResult.class)
    public ApiResult<Boolean> uploadHead(@RequestParam("head") MultipartFile multipartFile) throws Exception {
        log.info("multipartFile = " + multipartFile);
        log.info("ContentType = " + multipartFile.getContentType());
        log.info("OriginalFilename = " + multipartFile.getOriginalFilename());
        log.info("Name = " + multipartFile.getName());
        log.info("Size = " + multipartFile.getSize());

        // 上传文件，返回保存的文件名称
        String uploadPath = whyySystemProperties.getUploadPath();
        String saveFileName = UploadUtil.upload(uploadPath, multipartFile);
        // 上传成功之后，返回访问路径，请根据实际情况设置
        String headPath = whyySystemProperties.getResourceAccessPath() + saveFileName;
        log.info("headPath:{}", headPath);
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        boolean flag = sysUserService.updateSysUserHead(sysUser.getId(), headPath);
        if (flag) {
            return ApiResult.ok(headPath);
        }

        // 删除图片文件
        UploadUtil.deleteQuietly(uploadPath, saveFileName);
        return ApiResult.fail();
    }

    /**
     * 批量删除系统用户
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "批量删除SysUser对象", notes = "批量删除系统用户", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUser(@Valid @RequestBody List<Long> idList) throws Exception {
        boolean flag = sysUserService.deleteSysUsers(idList);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户状态
     */
    @PostMapping("/updateByState/{id}")
    @RequiresPermissions("sys:user:updateByState")
    @ApiOperation(value = "修改SysUser对象状态", notes = "修改用户表", response = ApiResult.class)
    public ApiResult<Boolean> updateByState(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.updateByState(id);
        return ApiResult.result(flag);
    }

    /**
     * 重置密码
     */
    @PostMapping("/uPassWord/{id}")
    @RequiresPermissions("sys:user:uPassWord")
    @ApiOperation(value = "修改密码", notes = "修改密码", response = ApiResult.class)
    public ApiResult<Boolean> uPassWord(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.uPassWord(id);
        return ApiResult.ok(flag);
    }

    @PostMapping("/passwordSetting")
    @RequiresPermissions("sys:user:uPassWord")
    @ApiOperation(value = "设置密码", notes = "设置密码", response = ApiResult.class)
    public ApiResult<Boolean> passwordSetting(@Valid @RequestBody PasswordQueryParam passwordQueryParam) throws Exception {
        boolean flag = sysUserService.passwordSetting(passwordQueryParam);
        return ApiResult.ok(flag);
    }

    /**
     * 批量修改系统用户状态
     */
    @PostMapping("/updateByStatus")
    @RequiresPermissions("sys:user:updateByStatus")
    @ApiOperation(value = "批量修改SysUser对象", notes = "批量修改系统用户状态", response = ApiResult.class)
    public ApiResult<Boolean> updateByStatus(@RequestBody Map<String, Object> params) throws Exception {
        Object ids = params.get("idList");
        List<String> idList = (List<String>) ids;
        String status = params.get("status").toString();
        boolean flag = sysUserService.updateByStatus(idList, status);
        return ApiResult.result(flag);
    }

    /**
     * 判断用户名是否存在
     */
    @GetMapping("/checkUserName/{username}")
    @RequiresPermissions("sys:user:checkUserName")
    @ApiOperation(value = "查看名称是否重复", notes = "查看系统用户名是否重复", response = SysUserQueryVo.class)
    public ApiResult<Boolean> checkUserName(@PathVariable("username") String username) throws Exception {
        boolean flag = sysUserService.checkUserName(username);
        return ApiResult.ok(flag);
    }

    /**
     * 修改用户基本信息
     */
    @PostMapping("/updateUserInformation")
    @RequiresPermissions("sys:user:updateUserInformation")
    @ApiOperation(value = "修改用户基本信息", notes = "修改用户基本信息", response = ApiResult.class)
    public ApiResult<Boolean> updateUserInformation(@Valid @RequestBody UpdateUserParam updateUserParam) throws Exception {
        boolean flag = sysUserService.updateUserInformation(updateUserParam);
        return ApiResult.result(flag);
    }

    /**
     * 根据机构id查询用户集合
     */
    @PostMapping("/findUserListByOfficeCode/{id}")
    @RequiresPermissions("sys:user:page")
    @ApiOperation(value = "获取SysUser集合", notes = "根据机构id获取系统用户集合", response = SysUserQueryVo.class)
    public ApiResult<List<SysUserQueryVo>> findUserListByOfficeCode(@PathVariable("id") String id) throws Exception {
        List<SysUserQueryVo> list = sysUserService.findUserListByOfficeCode(id);
        return ApiResult.ok(list);
    }

    /**
     * 根据用户ids查询用户集合
     */
    @PostMapping("/findUserListByIds/{ids}")
    @RequiresPermissions("sys:user:page")
    @ApiOperation(value = "根据用户ids查询用户集合", notes = "根据用户ids查询用户集合", response = SysUser.class)
    public ApiResult<List<SysUser>> findUserListByIds(@PathVariable("ids") String ids) throws Exception {
        List<SysUser> list = sysUserService.findUserListByIds(ids);
        return ApiResult.ok(list);
    }

    /**
     * 根据当前用户查询用户角色集合
     */
    @PostMapping("/getRoleList")
    @ApiOperation(value = "获取SysRole集合", notes = "根据当前用户查询用户角色集合", response = SysRoleQueryVo.class)
    public ApiResult<List<SysRoleQueryVo>> getRoleList() throws Exception {
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        List<SysRoleQueryVo> roleList = new ArrayList<>();

        if ((sysUser.getUserType().equals("2") || sysUser.getUserType().equals("3")) && whyySystemProperties.isEnableHwXueyuanWx() && StrUtil.isNotBlank(sysUser.getIsWx()) && sysUser.getIsWx().equals("0")) {
            String roleType = "studentnowx";
            if (sysUser.getUserType().equals("3")) {
                roleType = "teachernowx";
            }
            SysRoleQueryVo sysRoleQueryVo = sysRoleService.getSysRoleQueryByType(roleType);
            roleList.add(sysRoleQueryVo);
        } else {
            roleList = sysRoleService.getRoleList(sysUser.getId());
        }
        return ApiResult.ok(roleList);
    }

    /**
     * 判断用户名是否存在
     */
    @PostMapping("/checkUserNameValid")
    @RequiresPermissions("sys:user:add")
    @ApiOperation(value = "查看名称是否重复", notes = "查看系统用户名是否重复", response = SysUserQueryVo.class)
    public ApiResult<Boolean> checkUserNameValid(@Valid @RequestBody CheckUserNameParam checkUserNameParam) throws Exception {
        boolean flag = sysUserService.checkUserNameValid(checkUserNameParam);
        return ApiResult.ok(flag);
    }
}

