package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysMsgRecord;
import com.io.yy.system.param.SysMsgRecordQueryParam;
import com.io.yy.system.vo.SysMessageAndWxTemplateQueryVo;
import com.io.yy.system.vo.SysMsgRecordQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统消息记录表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Repository
public interface SysMsgRecordMapper extends BaseMapper<SysMsgRecord> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysMsgRecordQueryVo getSysMsgRecordById(Long id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysMsgRecordQueryParam
     * @return
     */
    IPage<SysMsgRecordQueryVo> getSysMsgRecordPageList(@Param("page") Page page, @Param("param") SysMsgRecordQueryParam sysMsgRecordQueryParam);

    boolean deleteSysMsgRecordByMsgId(Long id);

    void deleteSysMsgRecordByMsgIds(@Param("listMsgId") List<Long> listMsgId);

    SysMessageAndWxTemplateQueryVo getWxTemplate(Long id);
}
