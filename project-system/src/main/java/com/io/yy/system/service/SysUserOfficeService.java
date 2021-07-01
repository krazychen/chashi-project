package com.io.yy.system.service;

import com.io.yy.system.entity.SysUserOffice;
import com.io.yy.system.param.SysUserOfficeQueryParam;
import com.io.yy.system.vo.SysUserOfficeQueryVo;
import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 用户和机构关联表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
public interface SysUserOfficeService extends BaseService<SysUserOffice> {

    /**
     * 保存
     *
     * @param sysUserOffice
     * @return
     * @throws Exception
     */
    boolean saveSysUserOffice(SysUserOffice sysUserOffice) throws Exception;

    /**
     * 修改
     *
     * @param sysUserOffice
     * @return
     * @throws Exception
     */
    boolean updateSysUserOffice(SysUserOffice sysUserOffice) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysUserOffice(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysUserOffices(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysUserOfficeQueryVo getSysUserOfficeById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysUserOfficeQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysUserOfficeQueryVo> getSysUserOfficePageList(SysUserOfficeQueryParam sysUserOfficeQueryParam) throws Exception;

}
