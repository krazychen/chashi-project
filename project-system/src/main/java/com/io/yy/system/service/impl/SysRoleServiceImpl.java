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

package com.io.yy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.enums.StateEnum;
import com.io.yy.system.entity.SysRole;
import com.io.yy.system.mapper.SysRoleMapper;
import com.io.yy.system.param.SysRoleQueryParam;
import com.io.yy.system.param.SysRoleStateQueryParam;
import com.io.yy.system.service.SysRoleService;
import com.io.yy.system.service.SysUserService;
import com.io.yy.system.vo.SysRoleQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;


/**
 * <pre>
 * 系统角色 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2019-10-24
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRole(SysRole sysRole) throws Exception {
        // 保存系统用户
        sysRole.setState(1);
        return super.save(sysRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysRole(SysRole sysRole) throws Exception {
        return super.updateById(sysRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysRole(Long id) throws Exception {
        return sysRoleMapper.deleteRole(id)>0;
    }

    @Override
    public SysRoleQueryVo getSysRoleById(Serializable id) throws Exception {
        return sysRoleMapper.getSysRoleById(id);
    }

    @Override
    public Paging<SysRoleQueryVo> getSysRolePageList(SysRoleQueryParam sysRoleQueryParam) throws Exception {
        Page page = setPageParam(sysRoleQueryParam, OrderItem.desc("create_time"));
        IPage<SysRoleQueryVo> iPage = sysRoleMapper.getSysRolePageList(page, sysRoleQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean isEnableSysRole(Long id) throws Exception {
        SysRole sysRole = new SysRole()
                .setId(id)
                .setState(StateEnum.ENABLE.getCode());
        int count = sysRoleMapper.selectCount(new QueryWrapper<>(sysRole));
        return count > 0;
    }

    @Override
    public boolean isExistsByCode(String code) throws Exception {
        SysRole sysRole = new SysRole().setCode(code);
        return sysRoleMapper.selectCount(new QueryWrapper<>(sysRole)) > 0;
    }

    @Override
    public Boolean updateStateById(SysRoleStateQueryParam sysRoleStateQueryParam) {
        return sysRoleMapper.updateStateById(sysRoleStateQueryParam)>0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysRoles(List<String> idList) {
        try {
            for(String id:idList){
                sysRoleMapper.deleteRole(Long.parseLong(id));
            }
        } catch (Exception e) {
            throw new BusinessException("未知异常");
        }
        return true;
    }

    @Override
    public List<SysRoleQueryVo> getRoleList(Long id) {
        return sysRoleMapper.getRoleList(id);
    }

    @Override
    public SysRole getSysRoleByType(String type) {
        return sysRoleMapper.getSysRoleByType(type);
    }

    @Override
    public SysRoleQueryVo getSysRoleQueryByType(String type) {
        return sysRoleMapper.getSysRoleQueryByType(type);
    }


}
