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

package com.io.yy.shiro.vo;

import com.io.yy.system.entity.SysMenu;
import com.io.yy.system.entity.SysRole;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 登录用户对象，响应给前端
 * </p>
 *
 * @author kris
 * @date 2019-05-15
 **/
@Data
@Accessors(chain = true)
public class LoginSysUserVo implements Serializable {

    private static final long serialVersionUID = -1758338570596088158L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "性别，0：女，1：男，默认1")
    private Integer gender;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer state;

//    @ApiModelProperty("部门id")
//    private Long departmentId;
//
//    @ApiModelProperty("部门名称")
//    private String departmentName;

    @ApiModelProperty("机构代码")
    private String officeCode;

    @ApiModelProperty("机构名称")
    private String officeName;

    @ApiModelProperty("账户默认规则")
    private String defaultAccountRules;

//    @ApiModelProperty("角色id")
//    private Long roleId;
//
//    @ApiModelProperty("角色名称")
//    private String roleName;
//
//    @ApiModelProperty("角色编码")
//    private String roleCode;

    @ApiModelProperty("角色列表")
    private Set<SysRole> sysRoleList;

    @ApiModelProperty("菜单权限列表")
    private Set<SysMenuQueryVo> sysMenuList;

//    @ApiModelProperty("权限编码列表")
//    private Set<String> permissionCodes;
    @ApiModelProperty("是否绑定微信")
    private Boolean iswx;

    @ApiModelProperty("是否是超级管理员")
    private Boolean isSuperAdmin;
}
