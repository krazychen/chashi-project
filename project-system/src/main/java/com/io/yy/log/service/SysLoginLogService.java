
package com.io.yy.log.service;

import com.io.yy.common.service.BaseService;
import com.io.yy.common.vo.Paging;
import com.io.yy.log.entity.SysLoginLog;
import com.io.yy.log.param.SysLoginLogPageParam;


/**
 * 系统登录日志 服务类
 *
 * @author kris
 * @since 2020-03-24
 */
public interface SysLoginLogService extends BaseService<SysLoginLog> {

    /**
     * 保存
     *
     * @param sysLoginLog
     * @return
     * @throws Exception
     */
    boolean saveSysLoginLog(SysLoginLog sysLoginLog) throws Exception;

    /**
     * 修改
     *
     * @param sysLoginLog
     * @return
     * @throws Exception
     */
    boolean updateSysLoginLog(SysLoginLog sysLoginLog) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysLoginLog(Long id) throws Exception;


    /**
     * 获取分页对象
     *
     * @param sysLoginLogPageParam
     * @return
     * @throws Exception
     */
    Paging<SysLoginLog> getSysLoginLogPageList(SysLoginLogPageParam sysLoginLogPageParam) throws Exception;

}
