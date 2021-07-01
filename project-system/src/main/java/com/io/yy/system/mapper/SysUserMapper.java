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

package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.shiro.vo.WxOpenQueryVo;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.param.PasswordQueryParam;
import com.io.yy.system.param.SysUserQueryParam;
import com.io.yy.system.vo.SysUserQueryVo;
import com.io.yy.system.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统用户 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysUserVo getSysUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysUserQueryParam
     * @return
     */
    IPage<SysUserQueryVo> getSysUserPageList(@Param("page") Page page, @Param("param") SysUserQueryParam sysUserQueryParam);


    boolean updateStatus(@Param("idList") List<String> idList, @Param("status") String status);

    /**
     * 根据角色id获取此角色分配的用户
     * @param id
     * @return
     */
    List<SysUserQueryVo> getSysRoleAndUserList(String id);

    /**
     * 根据角色id获取此角色分配的用户的分页列表
     * @param sysUserQueryParam
     * @return
     */
    IPage<SysUserQueryVo> getSysRoleAndUserPageList(@Param("page") Page page, @Param("param") SysUserQueryParam sysUserQueryParam);

    /**
     * 根据多个角色查询用户集合
     * @param roles
     * @return
     */
    List<SysUser> getUserByRoleIds(String roles);

    /**
     * 根据机构id查询用户集合
     * @param id
     * @return
     */
    List<SysUserQueryVo> findUserListByOfficeCode(String id);

    /**
     * 根据unionid获取
     * @param openid
     * @return
     */
    WxOpenQueryVo getSysUserByOpenid(String openid);

    /**
     * 根据openid获取
     * @param openid
     * @return
     */
    WxOpenQueryVo getSysUserByOpenidTwo(String openid);

    Integer updateSysUserOpenidById(@Param("id") Long id,@Param("openid") String openid);

    Integer updateSysUserUnionidOpenidById(@Param("id") Long id,@Param("unionid") String unionid,@Param("openid") String openid);

    //根据openid添加unionid关联
    Integer updateSysUserUnionidByOpenid(@Param("unionid") String unionid,@Param("openid") String openid);

    /**
     * 解绑微信
     * @param id
     * @return
     */
    boolean noWx(Long id);

    /**
     * 批量解绑微信
     * @param idList
     * @return
     */
    boolean nowxs(@Param("idList") List<Long> idList);

    Integer updatePasswordById(@Param("param") PasswordQueryParam passwordQueryParam);

    Integer updateIsWxByOpenid(@Param("openid") String openid,@Param("isWx") String isWx);

    /**
     * 根据cctalk订单号获取关联的星舟用户
     * @param id
     * @return
     */
    List<SysUser> getSysUserByStudentOn(String id);

    List<SysUserQueryVo> getMsgSysUserList(@Param("userIds") String[] userIds);

    /**
     * 根据班级题型获取一级老师
     * @param classIds
     * @param classSubjectIds
     * @param roleIds
     * @return
     */
    List<SysUser> getMsgListUser(@Param("classIds") String[] classIds, @Param("classSubjectIds") String[] classSubjectIds, @Param("roleId") String roleIds);

    /**
     * 根据班级获取所有的学员
     * @param classIds
     * @return
     */
    List<SysUser> getStudentListUserByClassIds(@Param("classIds") String[] classIds);

    /**
     * 根据角色获取所有的用户
     * @param roleIds
     * @return
     */
    List<SysUser> getListUserByRoleId(@Param("roleId") String roleIds);

    /**
     * 根据班级获所有的老师
     * @param classIds
     * @return
     */
    List<SysUser> getTeacherListUserByClassIds(@Param("classIds") String[] classIds);

    /**
     * 根据作业id获取作业对应的学员
     * @param homeworkIds
     * @return
     */
    List<SysUser> getlistUserByHomeworkIds(@Param("homeworkIds")String[] homeworkIds);
}
