package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysMenu;
import com.io.yy.system.param.SysMenuQueryParam;
import com.io.yy.system.param.SysMenuTypeQueryParam;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 菜单表 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysMenuQueryVo getSysMenuById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysMenuQueryParam
     * @return
     */
    IPage<SysMenuQueryVo> getSysMenuPageList(@Param("page") Page page, @Param("param") SysMenuQueryParam sysMenuQueryParam);

    /**
     * 根据菜单code修改是否末级节点
     *
     * @param sysMenuCode
     * @param value
     * @return
     */
    Integer updateTreeLeafById(@Param("sysMenuCode") String sysMenuCode, @Param("value") String value);

    /**
     *获取精简版的树形结构
     * @param sysMenuTypeQueryParam
     * @return
     */
    List<SysMenuTreeQueryVo> getSysMenuSimplifyPageList(@Param("param") SysMenuTypeQueryParam sysMenuTypeQueryParam);

    /**
     * 获取角色关联的菜单
     * @param id
     * @return
     */
    List<SysMenuTreeQueryVo> getSysMenuByRoleId(String id);

    /**
     * 根据角色id获取用户菜单list
     * @param map
     * @return
     */
    List<SysMenuQueryVo> getSysMenuListByRoleId(Map<String, Object> map);

    /**
     * 根据用户id获取用户菜单list
     * @param map
     * @return
     */
    List<SysMenuQueryVo> getSysMenuListByUserId(Map<String, Object> map);
}
