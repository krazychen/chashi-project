package com.io.yy.system.service;

import com.io.yy.system.entity.SysMsgRecord;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysMsgRecordQueryParam;
import com.io.yy.system.vo.SysMsgRecordQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 系统消息记录表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
public interface SysMsgRecordService extends BaseService<SysMsgRecord> {

    /**
     * 保存
     *
     * @param sysMsgRecord
     * @return
     * @throws Exception
     */
    boolean saveSysMsgRecord(SysMsgRecord sysMsgRecord) throws Exception;

    /**
     * 修改
     *
     * @param sysMsgRecord
     * @return
     * @throws Exception
     */
    boolean updateSysMsgRecord(SysMsgRecord sysMsgRecord) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysMsgRecord(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysMsgRecords(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysMsgRecordQueryVo getSysMsgRecordById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysMsgRecordQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysMsgRecordQueryVo> getSysMsgRecordPageList(SysMsgRecordQueryParam sysMsgRecordQueryParam) throws Exception;

    /**
     * 修改消息记录表状态和时间
     * @param id
     * @return
     */
    boolean updateByRecord(String id)throws Exception;

    /**
     * 查询未读消息记录数
     * @param id
     * @return
     */
    Integer findMsgRecordCount(Long id)throws Exception;
}
