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

package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.entity.BaseEntity;
import com.io.yy.util.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * SystemUser
 * </p>
 *
 * @author kris
 * @since 2019-10-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUser对象", description = "系统用户")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    @ExcelField(title="用户名", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    private String username;

    @ApiModelProperty(value = "昵称")
    @ExcelField(title="昵称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    private String nickname;

    @ApiModelProperty(value = "登录密码")
    //@NotBlank(message = "登录密码不能为空")
    private String password;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "办公电话")
    private String mobile;

    @ApiModelProperty(value = "手机号码")
    @ExcelField(title="手机号码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private String phone;

    @ApiModelProperty(value = "性别，0：女，1：男，默认1")
    private Integer gender;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "个性签名")
    private String signtext;

    @ApiModelProperty(value = "绑定的微信号")
    private String wxOpenid;

    @ApiModelProperty(value = "绑定的微信openid")
    private String wxOpenidTwo;

    @ApiModelProperty(value = "是否绑定微信公众号")
    private String isWx;

    @ApiModelProperty(value = "绑定的手机串号")
    private String mobileImei;

    @ApiModelProperty(value = "用户类型")
    @NotBlank(message = "用户类型不能为空")
    private String userType;

    @ApiModelProperty(value = "用户类型引用编号")
    private String refCode;

    @ApiModelProperty(value = "用户类型引用姓名")
    private String refName;

    @ApiModelProperty(value = "管理员类型（0非管理员 1系统管理员  2二级管理员）")
    @NotBlank(message = "管理员类型（0非管理员 1系统管理员  2二级管理员）不能为空")
    private String mgrType;

    @ApiModelProperty(value = "开通方式（1:手工;2:注册;3:导入;）")
    private String regType;

    @ApiModelProperty(value = "密码安全级别（0初始 1很弱 2弱 3安全 4很安全）")
    private BigDecimal pwdSecurityLevel;

    @ApiModelProperty(value = "密码最后更新时间")
    private Date pwdUpdateDate;

    @ApiModelProperty(value = "密码修改记录")
    private String pwdUpdateRecord;

    @ApiModelProperty(value = "密保问题")
    private String pwdQuestion;

    @ApiModelProperty(value = "密保问题答案")
    private String pwdQuestionAnswer;

    @ApiModelProperty(value = "密保问题2")
    private String pwdQuestion2;

    @ApiModelProperty(value = "密保问题答案2")
    private String pwdQuestionAnswer2;

    @ApiModelProperty(value = "密保问题3")
    private String pwdQuestion3;

    @ApiModelProperty(value = "密保问题答案3")
    private String pwdQuestionAnswer3;

    @ApiModelProperty(value = "密码问题修改时间")
    private Date pwdQuestUpdateDate;

    @ApiModelProperty(value = "最后登陆IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后登陆时间")
    private Date lastLoginDate;

    @ApiModelProperty(value = "冻结时间")
    private Date freezeDate;

    @ApiModelProperty(value = "冻结原因")
    private String freezeCause;

    @ApiModelProperty(value = "用户权重（降序）")
    private BigDecimal userWeight;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "机构id")
    @TableField(exist=false)
    private String officeId;

    @ApiModelProperty(value = "机构name")
    @TableField(exist=false)
    private String officeName;

//    @ApiModelProperty(value = "部门id")
//    private Long departmentId;
//
//    @ApiModelProperty(value = "角色id")
//    private Long roleId;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer state;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 用户表的租户信息手工设置 **/
    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}
