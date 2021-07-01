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

package com.io.yy.system.service;

import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.param.*;
import com.io.yy.system.vo.SysUserQueryVo;
import com.io.yy.system.vo.SysUserVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 系统用户 服务类
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 保存
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    boolean saveSysUser(SysUser sysUser) throws Exception;

    /**
     * 修改
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    boolean updateSysUser(SysUser sysUser) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysUser(Long id) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysUserVo getSysUserById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysUserQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysUserQueryVo> getSysUserPageList(SysUserQueryParam sysUserQueryParam) throws Exception;

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     * @throws Exception
     */
    boolean isExistsByUsername(String username) throws Exception;

    /**
     * 检验机构是否存在并且已启用
     *
     * @param officeId
     * @throws Exception
     */
    void checkOffice(String officeId)  throws Exception;


    /**
     * 修改密码
     *
     * @param updatePasswordParam
     * @return
     * @throws Exception
     */
    boolean updatePassword(UpdatePasswordParam updatePasswordParam) throws Exception;

    /**
     * 修改系统用户头像
     *
     * @param id
     * @param headPath
     * @return
     * @throws Exception
     */
    boolean updateSysUserHead(Long id, String headPath) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysUsers(List<Long> idList) throws Exception;

    boolean updateByState(Long id);

    boolean uPassWord(Long id) throws Exception;

    boolean updateByStatus(List<String> idList, String status);

    boolean checkUserName(String username);

    /**
     * 获取已经关联角色的用户
     *
     * @param map
     * @return
     */
    List<SysUserQueryVo> getSysRoleAndUserList(Map<String, Object> map);

    /**
     * 获取关联了某个角色的所有用户的分页列表
     *
     * @param sysUserQueryParam
     * @return
     */
    Paging<SysUserQueryVo> getSysRoleAndUserPageList(SysUserQueryParam sysUserQueryParam);

    /**
     * 修改用户基本信息
     * @param updateUserParam
     * @return
     * @throws Exception
     */
    boolean updateUserInformation(UpdateUserParam updateUserParam)throws Exception;

    /**
     * 根据机构id查询用户集合
     * @param id
     * @return
     * @throws Exception
     */
    List<SysUserQueryVo> findUserListByOfficeCode(String id)throws Exception;

    /**
     * 根据用户ids查询用户集合
     * @param ids
     * @return
     * @throws Exception
     */
    List<SysUser> findUserListByIds(String ids)throws Exception;

    boolean noWx(Long id);

    boolean nowxs(List<Long> idList);

    boolean passwordSetting(PasswordQueryParam passwordQueryParam);

    /**
     * 判断用户是否存在
     * @param checkUserNameParam
     * @return
     */
    Boolean checkUserNameValid(CheckUserNameParam checkUserNameParam);
}
