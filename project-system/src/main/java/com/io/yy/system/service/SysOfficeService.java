package com.io.yy.system.service;

import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysOffice;
import com.io.yy.system.param.SysOfficeQueryParam;
import com.io.yy.system.vo.SysOfficeQueryVo;
import com.io.yy.system.vo.SysOfficeTreeQueryVo;
import com.io.yy.system.vo.SysOfficeUserTreeQueryVo;

import java.util.List;


/**
 * <pre>
 * 组织机构表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-23
 */
public interface SysOfficeService extends BaseService<SysOffice> {

    /**
     * 保存
     *
     * @param sysOffice
     * @return
     * @throws Exception
     */
    boolean saveSysOffice(SysOffice sysOffice) throws Exception;

    /**
     * 修改
     *
     * @param sysOffice
     * @return
     * @throws Exception
     */
    boolean updateSysOffice(SysOffice sysOffice) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysOffice(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysOffices(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysOfficeQueryVo getSysOfficeById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysOfficeQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysOfficeQueryVo> getSysOfficePageList(SysOfficeQueryParam sysOfficeQueryParam) throws Exception;

    boolean updateBystatus(String id);

    List<SysOfficeTreeQueryVo> getParentCodeTree(SysOfficeQueryParam sysOfficeQueryParam)throws Exception;

    boolean isEnableSysOffice(String departmentId);

    List<SysOfficeUserTreeQueryVo> getParentCodeUserTree(SysOfficeQueryParam sysOfficeQueryParam)throws Exception;
}
