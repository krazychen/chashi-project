package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.system.entity.SysRoleMenu;
import com.io.yy.system.param.SysRoleMenuQueryParam;
import com.io.yy.system.vo.SysRoleMenuQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 角色与菜单关联表 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-03
 */
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysRoleMenuQueryVo getSysRoleMenuById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysRoleMenuQueryParam
     * @return
     */
    IPage<SysRoleMenuQueryVo> getSysRoleMenuPageList(@Param("page") Page page, @Param("param") SysRoleMenuQueryParam sysRoleMenuQueryParam);


    /**
     * 批量添加关联
     * @param map
     * @return
     */
    Boolean addRoleAndMenu(@Param("param") Map<String, Object> map);

    /**
     * 删除用户角色关联
     * @param roleId
     */
    void deleteRoleMenuByRoleId(String roleId);

    List<String> getSysRoleMenuCodeByRoleId(String id);

    Integer deleteByMenuCodes(@Param("listCode") List<String> listCode,@Param("id") String id);
}
