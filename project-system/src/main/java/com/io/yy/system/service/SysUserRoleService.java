package com.io.yy.system.service;

import com.io.yy.system.entity.SysUserRole;
import com.io.yy.system.param.SysUserRolePLQueryParam;
import com.io.yy.system.param.SysUserRoleQueryParam;
import com.io.yy.system.vo.SysUserRoleQueryVo;
import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;

import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 用户和角色关联表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
public interface SysUserRoleService extends BaseService<SysUserRole> {

    /**
     * 保存
     *
     * @param sysUserRole
     * @return
     * @throws Exception
     */
    boolean saveSysUserRole(SysUserRole sysUserRole) throws Exception;

    /**
     * 修改
     *
     * @param sysUserRole
     * @return
     * @throws Exception
     */
    boolean updateSysUserRole(SysUserRole sysUserRole) throws Exception;

    /**
     * 删除
     *
     * @param map
     * @return
     * @throws Exception
     */
    boolean deleteSysUserRole(Map<String, Object> map) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysUserRoles(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysUserRoleQueryVo getSysUserRoleById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysUserRoleQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysUserRoleQueryVo> getSysUserRolePageList(SysUserRoleQueryParam sysUserRoleQueryParam) throws Exception;

    String findUserById(Long userId) throws Exception;

    boolean addRoles(String userId, String roleId);

    boolean addRole(String userId, String roleId);

    /**
     * 批量添加角色用户关联
     *
     * @param sysUserRoleQueryParam
     * @return
     */
    boolean addRoleAndUser(SysUserRoleQueryParam sysUserRoleQueryParam);

    /**
     * 批量新增角色和用户
     * @param params
     * @return
     */
    boolean addRolesUser(SysUserRolePLQueryParam params);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     * @throws Exception
     */
    List<SysUserRoleQueryVo> getUserRoleByUserId(Long userId) throws Exception;
}
