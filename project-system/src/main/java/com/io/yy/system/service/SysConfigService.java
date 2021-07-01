package com.io.yy.system.service;

import com.io.yy.system.entity.SysConfig;
import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysConfig;
import com.io.yy.system.param.SysConfigQueryParam;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.system.vo.SysConfigQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 参数配置表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-02
 */
public interface SysConfigService extends BaseService<SysConfig> {

    /**
     * 保存
     *
     * @param sysConfig
     * @return
     * @throws Exception
     */
    boolean saveSysConfig(SysConfig sysConfig) throws Exception;

    /**
     * 修改
     *
     * @param sysConfig
     * @return
     * @throws Exception
     */
    boolean updateSysConfig(SysConfig sysConfig) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysConfig(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysConfigs(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysConfigQueryVo getSysConfigById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysConfigQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysConfigQueryVo> getSysConfigPageList(SysConfigQueryParam sysConfigQueryParam) throws Exception;

    /**
     * 获取所有系统配置数据
     * @return
     */
    List<SysConfigDataRedisVo> getAllSysConfigData();
}
