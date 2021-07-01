package com.io.yy.system.service;

import com.io.yy.system.entity.SysMenu;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysMenuQueryParam;
import com.io.yy.system.param.SysMenuTypeQueryParam;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SysMenuTreeQueryVo;

import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 菜单表 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
public interface SysMenuService extends BaseService<SysMenu> {

    /**
     * 保存
     *
     * @param sysMenu
     * @return
     * @throws Exception
     */
    boolean saveSysMenu(SysMenu sysMenu) throws Exception;

    /**
     * 修改
     *
     * @param sysMenu
     * @return
     * @throws Exception
     */
    boolean updateSysMenu(SysMenu sysMenu) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysMenu(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysMenus(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysMenuQueryVo getSysMenuById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysMenuQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysMenuQueryVo> getSysMenuPageList(SysMenuQueryParam sysMenuQueryParam) throws Exception;

    /**
     * 获取菜单树形结构精简版
     * @param sysMenuTypeQueryParam
     * @return
     */
    List<SysMenuTreeQueryVo> getSysMenuSimplifyPageList(SysMenuTypeQueryParam sysMenuTypeQueryParam);

    /**
     * 根据角色id获取用户的菜单
     * @param map
     * @return
     */
    List<SysMenuTreeQueryVo> getSysMenuByRoleId(Map<String, Object> map);

    /**
     * 根据角色id获取用户菜单list
     * @param roleId
     * @return
     */
    List<SysMenuQueryVo> getSysMenuListByRoleId(String roleId);

    /**
     * 根据用户id获取用户菜单list
     * @param userId
     * @return
     */
    List<SysMenuQueryVo> getSysMenuListByUserId(String userId);
}
