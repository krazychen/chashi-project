package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysSchool;
import com.io.yy.system.param.SysSchoolParam;
import com.io.yy.system.param.SysSchoolQueryParam;
import com.io.yy.system.vo.SysSchoolQueryVo;
import com.io.yy.system.vo.SchoolListQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 学校表 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Repository
public interface SysSchoolMapper extends BaseMapper<SysSchool> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysSchoolQueryVo getSysSchoolById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysSchoolQueryParam
     * @return
     */
    IPage<SysSchoolQueryVo> getSysSchoolPageList(@Param("page") Page page, @Param("param") SysSchoolQueryParam sysSchoolQueryParam);

    List<SchoolListQueryVo> getSchoolList();

    SysSchoolQueryVo findByName(@Param("schoolName") String schoolName);

    /**
     * 根据省份编码，获取学校
     * @param sysSchoolParam
     * @return
     */
    List<SchoolListQueryVo> getSchoolListByCode(@Param("param") SysSchoolParam sysSchoolParam);

    /**
     * 批量保存
     * @param listSchool
     */
    void insertBatch(@Param("listSchool") List<SysSchool> listSchool);

    List<SysSchool> getSysSchoolBySchoolCodeList(@Param("schoolCodeList") List<String> schoolCodeList);
}
