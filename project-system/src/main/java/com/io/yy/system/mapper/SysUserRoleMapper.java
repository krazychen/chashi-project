package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.system.entity.SysUserRole;
import com.io.yy.system.param.SysUserRoleQueryParam;
import com.io.yy.system.vo.SysUserRoleQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * 用户和角色关联表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysUserRoleQueryVo getSysUserRoleById(Long id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysUserRoleQueryParam
     * @return
     */
    IPage<SysUserRoleQueryVo> getSysUserRolePageList(@Param("page") Page page, @Param("param") SysUserRoleQueryParam sysUserRoleQueryParam);

    boolean addRoles(@Param("roleList") List<SysUserRole> roleList);

    List<String> findUserById(@Param("userId") Long userId);

    void deleteListByUserIds(@Param("userIds") List<String> userIds);

    void deleteRoleUserByRoleId(Long roleId);

    /**
     * 批量删除
     * @param roleIds
     */
    void deleteRoleUserByRoleIds(@Param("roleIds") String[] roleIds);

    boolean deleteRoleByStudentId(Long id);

    List<SysUserRoleQueryVo> getUserRoleByUserId(@Param("userId") Long userId);
}
