package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysMsg;
import com.io.yy.system.param.StudentSysMsgQueryParam;
import com.io.yy.system.param.SysMsgQueryParam;
import com.io.yy.system.vo.SysMsgQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统消息表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Repository
public interface SysMsgMapper extends BaseMapper<SysMsg> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysMsgQueryVo getSysMsgById(Long id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysMsgQueryParam
     * @return
     */
    IPage<SysMsgQueryVo> getSysMsgPageList(@Param("page") Page page, @Param("param") SysMsgQueryParam sysMsgQueryParam);

    /**
     * 学员分页消息对象
     * @param page
     * @param studentSysMsgQueryParam
     * @return
     */
    IPage<SysMsgQueryVo> getStudentPageList(@Param("page") Page page, @Param("param") StudentSysMsgQueryParam studentSysMsgQueryParam);

    List<Long> getSysMsgsByHomeworkId(@Param("homeworkId") Long homeworkId);
}
