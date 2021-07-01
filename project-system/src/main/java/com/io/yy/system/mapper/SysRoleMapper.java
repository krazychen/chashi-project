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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysRole;
import com.io.yy.system.param.SysRoleQueryParam;
import com.io.yy.system.param.SysRoleStateQueryParam;
import com.io.yy.system.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 系统角色 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysRoleQueryVo getSysRoleById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysRoleQueryParam
     * @return
     */
    IPage<SysRoleQueryVo> getSysRolePageList(@Param("page") Page page, @Param("param") SysRoleQueryParam sysRoleQueryParam);

    /**
     * 更新角色状态
     * @param sysRoleStateQueryParam
     * @return
     */
    Integer updateStateById(@Param("param") SysRoleStateQueryParam sysRoleStateQueryParam);


    /**
     * 关联删除角色，角色和菜单，角色和用户
     * @param id
     * @return
     */
    int deleteRole(Long id);

    SysRole findByRoleName(@Param("name") String name);

    Long findByRoleType(@Param("type") String type);

    List<SysRoleQueryVo> getRoleList(Long id);

    /**
     * 根据角色类型获取角色对象
     * @param type
     * @return
     */
    SysRole getSysRoleByType(String type);

    /**
     * 根据角色类型获取角色对象
     * @param type
     * @return
     */
    SysRoleQueryVo getSysRoleQueryByType(String type);
}
