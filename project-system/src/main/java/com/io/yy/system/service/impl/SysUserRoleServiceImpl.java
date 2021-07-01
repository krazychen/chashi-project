package com.io.yy.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.shiro.util.LoginUtil;
import com.io.yy.shiro.vo.LoginSysUserRedisVo;
import com.io.yy.system.entity.SysRoleMenu;
import com.io.yy.system.entity.SysUserRole;
import com.io.yy.system.mapper.SysUserRoleMapper;
import com.io.yy.system.param.SysUserRolePLQueryParam;
import com.io.yy.system.param.SysUserRoleQueryParam;
import com.io.yy.system.service.SysUserRoleService;
import com.io.yy.system.vo.SysUserRoleQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 用户和角色关联表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    private Set set;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUserRole(SysUserRole sysUserRole) throws Exception {
        return super.save(sysUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUserRole(SysUserRole sysUserRole) throws Exception {
        return super.updateById(sysUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUserRole(Map<String, Object> map) throws Exception {
        return super.removeByMap(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUserRoles(List<String> idList) throws Exception {
        return super.removeByIds(idList);
    }

    @Override
    public SysUserRoleQueryVo getSysUserRoleById(Long id) throws Exception {
        return sysUserRoleMapper.getSysUserRoleById(id);
    }

    @Override
    public Paging<SysUserRoleQueryVo> getSysUserRolePageList(SysUserRoleQueryParam sysUserRoleQueryParam) throws Exception {
        Page page = setPageParam(sysUserRoleQueryParam, OrderItem.desc("create_time"));
        IPage<SysUserRoleQueryVo> iPage = sysUserRoleMapper.getSysUserRolePageList(page, sysUserRoleQueryParam);
        return new Paging(iPage);
    }

    @Override
    public String findUserById(Long userId) throws Exception {
        List<String> rIds = sysUserRoleMapper.findUserById(userId);
        String RidStr = "";
        for (String id : rIds) {
            RidStr += id + ",";
        }
        return RidStr;
    }

    @Override
    public boolean addRoles(String userId, String roleId) {
        //新增sys_user_role
        String[] roleIds = roleId.split(",");
        String[] ids = userId.split(",");
        Set set = new HashSet();
        for (int i = 0; i < ids.length; i++) {
            set.add(ids[i]);
        }
        ids = Arrays.stream(set.toArray()).toArray(String[]::new);
        //新增前需要把之前的关联关系删除掉
        if (ids.length > 0) {
            List<String> userIds = Arrays.stream(ids).collect(Collectors.toList());
            sysUserRoleMapper.deleteListByUserIds(userIds);
        }
        List<SysUserRole> roleList = new ArrayList<>();
        if (roleIds.length > 0 && ids.length > 0) {
            for (int i = 0; i < roleIds.length; i++) {
                for (int j = 0; j < ids.length; j++) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(Long.valueOf(ids[j])).
                            setRoleId(Long.valueOf(roleIds[i])).
                            setDeleted(0);
//                            setVersion(0).
//                            setCorpCode("1").
//                            setCorpName("1");
                    roleList.add(userRole);
                }
            }
        } else {
            addRole(userId, roleId);
        }
        return super.saveBatch(roleList);
    }

    @Override
    public boolean addRole(String userId, String roleId) {
        List<String> uId = new ArrayList<>();
        uId.add(userId);
        sysUserRoleMapper.deleteListByUserIds(uId);
        //新增sys_user_role
        String[] roleIds = roleId.split(",");
        List<SysUserRole> roleList = new ArrayList<>();
        if (roleIds.length > 0) {
            for (int i = 0; i < roleIds.length; i++) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(Long.valueOf(userId)).
                        setRoleId(Long.valueOf(roleIds[i])).
                        setDeleted(0);
//                        setVersion(0).
//                        setCorpCode("1").
//                        setCorpName("1");
                roleList.add(userRole);
            }
        }
        return super.saveBatch(roleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addRoleAndUser(SysUserRoleQueryParam sysUserRoleQueryParam) {
        LoginSysUserRedisVo loginSysUserRedisVo = LoginUtil.getLoginSysUserRedisVo();
        if (ObjectUtil.isNull(loginSysUserRedisVo))
            throw new BusinessException("当前用户未登录");

        sysUserRoleMapper.deleteRoleUserByRoleId(sysUserRoleQueryParam.getId());
        Boolean judge = false;
        ArrayList<SysUserRole> list = null;
        if (ObjectUtil.isNotNull(sysUserRoleQueryParam.getIdList())) {
            Date date = new Date();
            list = CollUtil.newArrayList();
            for (Long id : sysUserRoleQueryParam.getIdList()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole
//                        .setCorpCode("")
//                        .setCorpName("")
                        .setCreateBy(loginSysUserRedisVo.getId().toString())
                        .setCreateTime(date)
                        .setUpdateBy(loginSysUserRedisVo.getId().toString())
                        .setRoleId(sysUserRoleQueryParam.getId())
                        .setUpdateTime(date)
                        .setUserId(id)
//                        .setVersion(1)
                        .setDeleted(0);
                list.add(sysUserRole);
            }
        }
        if (ObjectUtil.isNotNull(list) && ObjectUtil.isNotEmpty(list)) {
            judge = super.saveBatch(list);
        }
        return judge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addRolesUser(SysUserRolePLQueryParam params) {

        String[] ids = params.getUserids();
        String[] roleIds = params.getRoleids();
        //新增前需要把之前的关联关系删除掉
        sysUserRoleMapper.deleteRoleUserByRoleIds(roleIds);
        List<SysUserRole> roleList = new ArrayList<>();
        for (int i = 0; i < roleIds.length; i++) {
            for (int j = 0; j < ids.length; j++) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(Long.valueOf(ids[j])).
                        setRoleId(Long.valueOf(roleIds[i])).
                        setDeleted(0);
//                        setVersion(0).
//                        setCorpCode("1").
//                        setCorpName("1");
                roleList.add(userRole);
            }
        }
        return super.saveBatch(roleList);
    }

    /**
     * 根据用户id 获取角色信息
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<SysUserRoleQueryVo> getUserRoleByUserId(Long userId) throws Exception {
        return sysUserRoleMapper.getUserRoleByUserId(userId);
    }

}
