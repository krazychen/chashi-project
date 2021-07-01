package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysCollegeExamscope;
import com.io.yy.system.param.NewSysCollegeExamscopeQueryParam;
import com.io.yy.system.param.SysCollegeExamscopeQueryParam;
import com.io.yy.system.vo.SysCollegeExamscopeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 考试范围表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-14
 */
@Repository
public interface SysCollegeExamscopeMapper extends BaseMapper<SysCollegeExamscope> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysCollegeExamscopeQueryVo getSysCollegeExamscopeById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysCollegeExamscopeQueryParam
     * @return
     */
    IPage<SysCollegeExamscopeQueryVo> getSysCollegeExamscopePageList(@Param("page") Page page, @Param("param") SysCollegeExamscopeQueryParam sysCollegeExamscopeQueryParam);

    boolean deleteCollegeIds(@Param("collegeIds")List<String> collegeIds);

    /**
     * 批量添加
     * @param listCollegeExamscope
     */
    void insertBatch(@Param("listCollegeExamscope") List<NewSysCollegeExamscopeQueryParam> listCollegeExamscope);
}
