package com.io.yy.system.service;

import com.io.yy.system.entity.SysRoleMenu;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysRoleMenuQueryParam;
import com.io.yy.system.vo.SysRoleMenuQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 角色与菜单关联表 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-03
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

    /**
     * 保存
     *
     * @param sysRoleMenu
     * @return
     * @throws Exception
     */
    boolean saveSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception;

    /**
     * 修改
     *
     * @param sysRoleMenu
     * @return
     * @throws Exception
     */
    boolean updateSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysRoleMenu(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysRoleMenus(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysRoleMenuQueryVo getSysRoleMenuById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysRoleMenuQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysRoleMenuQueryVo> getSysRoleMenuPageList(SysRoleMenuQueryParam sysRoleMenuQueryParam) throws Exception;

    /**
     * 批量关联角色和菜单
     * @param sysRoleMenuQueryParam
     * @return
     */
    boolean addRoleAndMenu(SysRoleMenuQueryParam sysRoleMenuQueryParam);

}
