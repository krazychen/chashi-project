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
import com.io.yy.system.entity.SysRole;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.param.SysRoleQueryParam;
import com.io.yy.system.param.SysRoleStateQueryParam;
import com.io.yy.system.param.sysrole.AddSysRoleParam;
import com.io.yy.system.param.sysrole.UpdateSysRoleParam;
import com.io.yy.system.vo.SysRoleQueryVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 系统角色 服务类
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 保存
     *
     * @param sysRole
     * @return
     * @throws Exception
     */
    boolean saveSysRole(SysRole sysRole) throws Exception;

    /**
     * 修改
     *
     * @param sysRole
     * @return
     * @throws Exception
     */
    boolean updateSysRole(SysRole sysRole) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysRole(Long id) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysRoleQueryVo getSysRoleById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysRoleQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysRoleQueryVo> getSysRolePageList(SysRoleQueryParam sysRoleQueryParam) throws Exception;

    /**
     * 根据id校验角色是否存在并且已启用
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean isEnableSysRole(Long id) throws Exception;

    /**
     * 判断角色编码是否存在
     * @param code
     * @return
     * @throws Exception
     */
    boolean isExistsByCode(String code) throws Exception;


    /**
     * 更新角色状态
     * @param sysRoleStateQueryParam
     * @return
     */
    Boolean updateStateById(SysRoleStateQueryParam sysRoleStateQueryParam);

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysRoles(List<String> idList);

    /**
     * 根据当前用户查询用户角色集合
     * @param id
     * @return
     */
    List<SysRoleQueryVo> getRoleList(Long id);

    /**
     * 根据角色类型获取角色
     * @param type
     * @return
     */
    SysRole getSysRoleByType(String type);

    /**
     * 根据角色类型获取角色
     * @param type
     * @return
     */
    SysRoleQueryVo getSysRoleQueryByType(String type);
}
