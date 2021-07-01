package com.io.yy.system.service;

//import com.io.yy.homework.entity.HwHomework;
import com.io.yy.system.entity.SysMsg;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.StudentSysMsgQueryParam;
import com.io.yy.system.param.SysMsgQueryParam;
import com.io.yy.system.vo.SysMsgQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 系统消息表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
public interface SysMsgService extends BaseService<SysMsg> {

    /**
     * 保存
     *
     * @param sysMsg
     * @return
     * @throws Exception
     */
    boolean saveSysMsg(SysMsg sysMsg) throws Exception;

    /**
     * 修改
     *
     * @param sysMsg
     * @return
     * @throws Exception
     */
    boolean updateSysMsg(SysMsg sysMsg) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysMsg(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysMsgs(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysMsgQueryVo getSysMsgById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysMsgQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysMsgQueryVo> getSysMsgPageList(SysMsgQueryParam sysMsgQueryParam) throws Exception;

    /**
     * 发布消息
     * @param sysMsg
     * @return
     * @throws Exception
     */
    boolean publishMsg(SysMsg sysMsg)throws Exception;

    boolean cancelMsg(Long id)throws Exception;

    /**
     * 下发通知
     * @return
     * @throws Exception
     */
//    public Boolean sendMsg(HwHomework hwHomework, String userIds) throws Exception;

    /**
     * 通过调度任务发布消息
     * @param params
     * @return
     * @throws Exception
     */
    Boolean saveSysMsgRecord(String params)throws Exception;

    /**
     * 查询学员前台消息列表
     * @param studentSysMsgQueryParam
     * @return
     */
    Paging<SysMsgQueryVo> getStudentPageList(StudentSysMsgQueryParam studentSysMsgQueryParam);

    boolean publish(Long id) throws Exception;
}
